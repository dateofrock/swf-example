package com.dateofrock.example.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GrayScaleImageConverter {

	private static final Log log = LogFactory.getLog(GrayScaleImageConverter.class);

	public ImageOperationResult execute(File inputImageFile) {
		ImageOperationResult result = new ImageOperationResult();
		BufferedImage readImage = null;
		try {
			File tmpFile = File.createTempFile("GrayScale", ".jpg");
			readImage = ImageIO.read(inputImageFile);
			int width = readImage.getWidth();
			int height = readImage.getHeight();
			result.width = width;
			result.height = height;
			BufferedImage writeImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			writeImage.getGraphics().drawImage(readImage, 0, 0, null);
			ImageIO.write(writeImage, "jpeg", tmpFile);
			result.convertedFile = tmpFile;
			result.completed = true;
		} catch (IOException e) {
			log.error("グレースケール変換処理に失敗", e);
			result.completed = false;
		}
		return result;
	}

	/**
	 * お試し実行用
	 */
	public static void main(String[] args) throws Exception {
		GrayScaleImageConverter gc = new GrayScaleImageConverter();
		File inputImageFile = new File("mucha.jpg");
		ImageOperationResult result = gc.execute(inputImageFile);
		File outputImageFile = new File("mucha.gray.jpg");
		FileUtils.deleteQuietly(outputImageFile);
		FileUtils.moveFile(result.convertedFile, outputImageFile);
		result.convertedFile.delete();
	}
}
