package com.dateofrock.example.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

public class AWSUtils {

	private static Properties config;

	static {
		InputStream input = AWSUtils.class.getResourceAsStream("/config.properties");
		config = new Properties();
		try {
			config.load(input);
		} catch (Exception e) {
			throw new RuntimeException("/config.properties読み込みに失敗", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static String getSNSEndPoint() {
		return config.getProperty("snsEndPoint");
	}

	public static String getSWFEndPoint() {
		return config.getProperty("swfEndPoint");
	}

	public static String getSWFDomainName() {
		return config.getProperty("swfDomainName");
	}

	public static String getS3BucketName() {
		return config.getProperty("s3BucketName");
	}

	public static String getSNSTopicARN() {
		return config.getProperty("snsTopicARN");
	}

	public static String getSWFTaskListName() {
		return config.getProperty("swfTaskListName");
	}

	public static AmazonS3 s3Client() {
		AmazonS3 s3 = new AmazonS3Client(credentials());
		return s3;
	}

	public static AmazonSNS snsClient() {
		AmazonSNS sns = new AmazonSNSClient(credentials());
		sns.setEndpoint(getSNSEndPoint());
		return sns;
	}

	public static AmazonSimpleWorkflow swfClient() {
		AmazonSimpleWorkflow swf = new AmazonSimpleWorkflowClient(credentials());
		swf.setEndpoint(getSWFEndPoint());
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
