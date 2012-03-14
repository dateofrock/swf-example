package com.dateofrock.example;

import java.io.File;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.dateofrock.example.aws.AWSUtils;
import com.dateofrock.example.aws.swf.ExampleConstants;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowClientExternal;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowClientExternalFactory;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowClientExternalFactoryImpl;

public class Starter {

	public static void main(String[] args) {
		AmazonSimpleWorkflow swf = AWSUtils.swfClient();
		GrayScaleConvertWorkflowClientExternalFactory factory = new GrayScaleConvertWorkflowClientExternalFactoryImpl(
				swf, ExampleConstants.SWF_DOMAIN_NAME);
		GrayScaleConvertWorkflowClientExternal external = factory.getClient();

		File imageFile = new File("mucha.jpg");
		external.execute(imageFile);
	}

}
