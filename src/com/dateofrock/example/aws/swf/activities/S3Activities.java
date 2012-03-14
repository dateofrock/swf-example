package com.dateofrock.example.aws.swf.activities;

import java.io.File;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

@Activities(version = "2.0")
@ActivityRegistrationOptions(defaultTaskStartToCloseTimeoutSeconds = 120, defaultTaskScheduleToStartTimeoutSeconds = 60)
public interface S3Activities {

	@Activity(name = "example.S3Activities.upload")
	public S3Result upload(File imageFile);

	@Activity(name = "example.S3Activities.download")
	public File download(S3Result s3Result);

}
