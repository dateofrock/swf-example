package com.dateofrock.example.aws.swf.workflow;

import java.io.File;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;

@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 300)
public interface GrayScaleConvertWorkflow {

	@Execute(version = "1.0", name = "example.GrayScaleConvertWorkflow.execute")
	public void execute(File imageFile);

}
