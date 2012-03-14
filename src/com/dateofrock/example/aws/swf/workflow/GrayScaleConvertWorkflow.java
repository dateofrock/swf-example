package com.dateofrock.example.aws.swf.workflow;

import java.io.File;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;

@Workflow
// Maximum time that workflow run is allowed to execute. Workflow is forcefully
// closed by the SWF service if this timeout is exceeded.
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 300)
public interface GrayScaleConvertWorkflow {

	@Execute(version = "1.0", name = "example.GrayScaleConvertWorkflow.execute")
	public void execute(File imageFile);

}
