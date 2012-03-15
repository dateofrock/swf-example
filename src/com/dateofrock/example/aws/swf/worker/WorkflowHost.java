package com.dateofrock.example.aws.swf.worker;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import com.dateofrock.example.aws.AWSUtils;
import com.dateofrock.example.aws.swf.workflow.GrayScaleConvertWorkflowImpl;

public class WorkflowHost {

	public static void main(String[] args) throws Exception {
		AmazonSimpleWorkflow swf = AWSUtils.swfClient();
		WorkflowWorker worker = new WorkflowWorker(swf, AWSUtils.getSWFDomainName(), AWSUtils.getSWFTaskListName());
		worker.addWorkflowImplementationType(GrayScaleConvertWorkflowImpl.class);
		worker.start();
	}

}
