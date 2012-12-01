package com.dateofrock.example.aws.swf.activities;

import java.io.File;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.dateofrock.example.logic.ImageOperationResult;

@Activities(version = "2.0")
@ActivityRegistrationOptions(defaultTaskStartToCloseTimeoutSeconds = 120, defaultTaskScheduleToStartTimeoutSeconds = 60)
public interface ImageActivities {

	@Activity(name = "example.ImageActivities.convertToGrayScale")
	public ImageOperationResult convertToGrayScale(File inputImageFile);

}
