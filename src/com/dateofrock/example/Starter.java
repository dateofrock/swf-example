package com.dateofrock.example;

import java.io.File;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.dateofrock.example.aws.AWSUtils;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowClientExternal;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowClientExternalFactory;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowClientExternalFactoryImpl;

public class Starter {

	public static void main(String[] args) {
		AmazonSimpleWorkflow swf = AWSUtils.swfClient();
		GrayScaleConvertWorkflowClientExternalFactory factory = new GrayScaleConvertWorkflowClientExternalFactoryImpl(
				swf, AWSUtils.getSWFDomainName());
		GrayScaleConvertWorkflowClientExternal external = factory.getClient();

		File imageFile = new File("mucha.jpg");
		external.execute(imageFile);
	}

}
