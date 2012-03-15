package com.dateofrock.example.aws.swf.workflow;

import java.io.File;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.dateofrock.example.aws.swf.activities.ImageActivitiesClient;
import com.dateofrock.example.aws.swf.activities.ImageActivitiesClientImpl;
import com.dateofrock.example.aws.swf.activities.S3ActivitiesClient;
import com.dateofrock.example.aws.swf.activities.S3ActivitiesClientImpl;
import com.dateofrock.example.aws.swf.activities.S3Result;
import com.dateofrock.example.aws.swf.activities.SNSActivitiesClient;
import com.dateofrock.example.aws.swf.activities.SNSActivitiesClientImpl;
import com.dateofrock.example.logic.ImageOperationResult;

public class GrayScaleConvertWorkflowImpl implements GrayScaleConvertWorkflow {

	private S3ActivitiesClient s3ActivitiesClient = new S3ActivitiesClientImpl();
	private ImageActivitiesClient imageActivitiesClient = new ImageActivitiesClientImpl();
	private SNSActivitiesClient snsActivitiesClient = new SNSActivitiesClientImpl();

	@Override
	public void execute(File imageFile) {
		// 1.S3にアップロード
		Promise<S3Result> s3Result = this.s3ActivitiesClient.upload(imageFile);

		// 2.S3からダウンロード
		Promise<File> downloadTo = downloadFromS3(s3Result);

		// 3.画像コンバート
		Promise<ImageOperationResult> imageOpResult = convertToGrayScale(downloadTo);

		// 4.SNSで通知
		notifyOperationComplete(s3Result, imageOpResult);
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
