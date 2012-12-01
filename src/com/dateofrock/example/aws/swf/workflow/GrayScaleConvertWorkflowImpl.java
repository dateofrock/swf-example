package com.dateofrock.example.aws.swf.workflow;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;
import com.dateofrock.example.aws.swf.activities.ImageActivitiesClient;
import com.dateofrock.example.aws.swf.activities.ImageActivitiesClientImpl;
import com.dateofrock.example.aws.swf.activities.S3ActivitiesClient;
import com.dateofrock.example.aws.swf.activities.S3ActivitiesClientImpl;
import com.dateofrock.example.aws.swf.activities.S3Result;
import com.dateofrock.example.aws.swf.activities.SNSActivitiesClient;
import com.dateofrock.example.aws.swf.activities.SNSActivitiesClientImpl;
import com.dateofrock.example.logic.ImageOperationResult;

public class GrayScaleConvertWorkflowImpl implements GrayScaleConvertWorkflow {

	private static final Log log = LogFactory.getLog(GrayScaleConvertWorkflowImpl.class);

	private final S3ActivitiesClient s3ActivitiesClient = new S3ActivitiesClientImpl();
	private final ImageActivitiesClient imageActivitiesClient = new ImageActivitiesClientImpl();
	private final SNSActivitiesClient snsActivitiesClient = new SNSActivitiesClientImpl();

	@Override
	public void execute(final File imageFile) {

		new TryCatchFinally() {
			@Override
			protected void doTry() throws Throwable {
				// 1.S3にアップロード
				Promise<S3Result> s3Result = s3ActivitiesClient.upload(imageFile);

				// 2.S3からダウンロード
				Promise<File> downloadTo = downloadFromS3(s3Result);

				// 3.画像コンバート
				Promise<ImageOperationResult> imageOpResult = convertToGrayScale(downloadTo);

				// 4.SNSで通知
				notifyOperationComplete(s3Result, imageOpResult);
			}

			@Override
			protected void doCatch(Throwable e) throws Throwable {
				log.error("例外発生", e);// ログに記録
				snsActivitiesClient.notifyException(e);// 管理者にメール通知
				throw e;// 再スロー
			}

			@Override
			protected void doFinally() throws Throwable {
				// noop
			}
		};
	}

	@Asynchronous
	private Promise<File> downloadFromS3(Promise<S3Result> s3Result) {
		return this.s3ActivitiesClient.download(s3Result);
	}

	@Asynchronous
	private Promise<ImageOperationResult> convertToGrayScale(Promise<File> downloadTo) {
		return this.imageActivitiesClient.convertToGrayScale(downloadTo);
	}

	@Asynchronous
	private void notifyOperationComplete(Promise<S3Result> s3Result, Promise<ImageOperationResult> imageOpResult) {
		this.snsActivitiesClient.notifyOperationComplete(s3Result, imageOpResult);
	}
}
