package com.dateofrock.example.aws;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.dateofrock.example.aws.swf.ExampleConstants;

public class AWSUtils {

	public static AmazonS3 s3Client() {
		AmazonS3 s3 = new AmazonS3Client(credentials());
		return s3;
	}

	public static AmazonSNS snsClient() {
		AmazonSNS sns = new AmazonSNSClient(credentials());
		sns.setEndpoint(ExampleConstants.SNS_ENDPOINT);
		return sns;
	}

	public static AmazonSimpleWorkflow swfClient() {
		AmazonSimpleWorkflow swf = new AmazonSimpleWorkflowClient(credentials());
		swf.setEndpoint(ExampleConstants.SWF_ENDPOINT);
		return swf;
	}

	private static AWSCredentials credentials() {
		InputStream input = AWSUtils.class.getResourceAsStream("/AwsCredentials.properties");
		AWSCredentials cred = null;
		try {
			cred = new PropertiesCredentials(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return cred;
	}

}
