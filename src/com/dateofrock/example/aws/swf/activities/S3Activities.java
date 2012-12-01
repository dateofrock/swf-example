package com.dateofrock.example.aws.swf.activities;

import java.io.File;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;
import com.dateofrock.example.MyIOException;
import com.dateofrock.example.MyS3Exception;

@Activities(version = "2.0")
@ActivityRegistrationOptions(defaultTaskStartToCloseTimeoutSeconds = 120, defaultTaskScheduleToStartTimeoutSeconds = 60)
public interface S3Activities {

	@Activity(name = "example.S3Activities.upload")
	public S3Result upload(File imageFile) throws MyS3Exception;

	@Activity(name = "example.S3Activities.download")
	@ExponentialRetry(initialRetryIntervalSeconds = 5, maximumAttempts = 3, exceptionsToRetry = { MyS3Exception.class })
	public File download(S3Result s3Result) throws MyS3Exception, MyIOException;

}
