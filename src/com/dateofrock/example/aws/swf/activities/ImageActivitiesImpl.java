package com.dateofrock.example.aws.swf.activities;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dateofrock.example.logic.GrayScaleImageConverter;
import com.dateofrock.example.logic.ImageOperationResult;

public class ImageActivitiesImpl implements ImageActivities {

	private static final Log log = LogFactory.getLog(ImageActivitiesClientImpl.class);

	@Override
	public ImageOperationResult convertToGrayScale(File inputImageFile) {
		GrayScaleImageConverter converter = new GrayScaleImageConverter();
		log.info("グレースケール変換 スタート inputImageFile=>" + inputImageFile);
		ImageOperationResult result = converter.execute(inputImageFile);
		log.info("グレースケール変換 終了");
		return result;
	}

}
