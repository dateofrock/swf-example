package com.dateofrock.example.aws.swf.worker;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.dateofrock.example.aws.AWSUtils;
import com.dateofrock.example.aws.swf.ExampleConstants;
import com.dateofrock.example.aws.swf.activities.ImageActivitiesImpl;
import com.dateofrock.example.aws.swf.activities.S3ActivitiesImpl;
import com.dateofrock.example.aws.swf.activities.SNSActivitiesImpl;

public class ActivityHost {

	public static void main(String[] args) throws Exception {
		AmazonSimpleWorkflow swf = AWSUtils.swfClient();
		ActivityWorker worker = new ActivityWorker(swf, ExampleConstants.SWF_DOMAIN_NAME, ExampleConstants.SWF_TASK_LIST_NAME);
		worker.addActivitiesImplementation(new S3ActivitiesImpl());
		worker.addActivitiesImplementation(new ImageActivitiesImpl());
		worker.addActivitiesImplementation(new SNSActivitiesImpl());
		worker.start();
	}

}
