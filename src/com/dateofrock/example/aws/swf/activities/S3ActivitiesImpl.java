package com.dateofrock.example.aws.swf.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.dateofrock.example.MyIOException;
import com.dateofrock.example.MyS3Exception;
import com.dateofrock.example.aws.AWSUtils;

public class S3ActivitiesImpl implements S3Activities {

	private static final Log log = LogFactory.getLog(S3ActivitiesImpl.class);

	@Override
	public S3Result upload(File imageFile) throws MyS3Exception {
		log.info("アップロード スタート imageFile=>" + imageFile);

		S3Result result = new S3Result();

		AmazonS3 s3 = AWSUtils.s3Client();
		String bucketName = AWSUtils.getS3BucketName();
		String keyName = "images/" + imageFile.getName();

		try {
			s3.putObject(bucketName, keyName, imageFile);
		} catch (Exception e) {
			throw new MyS3Exception("アップロード失敗", e);
		}

		result.bucketName = bucketName;
		result.keyName = keyName;

		log.info("アップロード 終了");
		return result;
	}

	@Override
	public File download(S3Result s3Result) throws MyS3Exception, MyIOException {
		log.info("ダウンロード スタート");
		AmazonS3 s3 = AWSUtils.s3Client();
		InputStream input = null;
		OutputStream output = null;
		File downloadTo = null;
		try {
			S3Object s3Obj = s3.getObject(s3Result.bucketName, s3Result.keyName);
			input = s3Obj.getObjectContent();
			downloadTo = File.createTempFile("downloaded", ".jpg");
			output = new FileOutputStream(downloadTo);
			IOUtils.copy(input, output);
			log.info("ダウンロード 終了");
		} catch (AmazonClientException e) {
			throw new MyS3Exception("S3 APIで例外", e);
		} catch (IOException e) {
			throw new MyIOException("I/O例外", e);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
		return downloadTo;
	}

}
