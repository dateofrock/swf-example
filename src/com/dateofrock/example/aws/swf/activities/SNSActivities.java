package com.dateofrock.example.aws.swf.activities;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.dateofrock.example.logic.ImageOperationResult;

@Activities(version = "2.0")
@ActivityRegistrationOptions(defaultTaskStartToCloseTimeoutSeconds = 120, defaultTaskScheduleToStartTimeoutSeconds = 60)
public interface SNSActivities {

	@Activity(name = "example.SNSActivities.notifyOperationComplete")
	public void notifyOperationComplete(S3Result s3Result, ImageOperationResult imageOpResult);

	@Activity(name = "example.SNSActivities.notifyException")
	public void notifyException(Throwable e);

}
