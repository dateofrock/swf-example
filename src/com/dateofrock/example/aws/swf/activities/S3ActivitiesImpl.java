package com.dateofrock.example.aws.swf.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.dateofrock.example.aws.AWSUtils;

public class S3ActivitiesImpl implements S3Activities {

	private static final Log log = LogFactory.getLog(S3ActivitiesImpl.class);

	@Override
	public S3Result upload(File imageFile) {
		log.info("アップロード スタート imageFile=>" + imageFile);

		S3Result result = new S3Result();

		AmazonS3 s3 = AWSUtils.s3Client();
		String bucketName = AWSUtils.getS3BucketName();
		String keyName = "images/" + imageFile.getName();
		s3.putObject(bucketName, keyName, imageFile);

		result.bucketName = bucketName;
		result.keyName = keyName;

		log.info("アップロード 終了");
		return result;
	}

	@Override
	public File download(S3Result s3Result) {
		log.info("ダウンロード スタート");
		AmazonS3 s3 = AWSUtils.s3Client();
		S3Object s3Obj = s3.getObject(s3Result.bucketName, s3Result.keyName);
		InputStream input = s3Obj.getObjectContent();
		OutputStream output = null;
		File downloadTo = null;
		try {
			downloadTo = File.createTempFile("downloaded", ".jpg");
			output = new FileOutputStream(downloadTo);
			IOUtils.copy(input, output);
			log.info("ダウンロード 終了");
		} catch (Exception e) {
			log.error("ダウンロードで例外", e);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
		return downloadTo;
	}

}
