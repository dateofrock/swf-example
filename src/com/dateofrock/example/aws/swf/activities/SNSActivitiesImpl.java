package com.dateofrock.example.aws.swf.activities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.dateofrock.example.aws.AWSUtils;
import com.dateofrock.example.logic.ImageOperationResult;

public class SNSActivitiesImpl implements SNSActivities {

	private static final Log log = LogFactory.getLog(SNSActivitiesImpl.class);

	@Override
	public void notifyOperationComplete(S3Result s3Result, ImageOperationResult imageOpResult) {
		log.info("通知スタート");
		AmazonSNS sns = AWSUtils.snsClient();

		String subject = null;
		StringBuilder message = new StringBuilder();
		if (imageOpResult.completed) {
			subject = "GrayScale Operation Completed!";
			message.append("バケット名：" + s3Result.bucketName + "\n");
			message.append("キー：" + s3Result.keyName + "\n");
			message.append("画像幅：" + imageOpResult.width + "\n");
			message.append("画像高：" + imageOpResult.height + "\n");
			message.append("保存先：" + imageOpResult.convertedFile.getAbsolutePath());
		} else {
			subject = "GrayScale Operation Failed!";
		}
		sns.publish(new PublishRequest().withTopicArn(AWSUtils.getSNSTopicARN()).withMessage(message.toString())
				.withSubject(subject));
		log.info("通知終了");
	}

}
