package com.guilin.studycode.utils.image;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class ImageUtils {

	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);

	private static Encoder encoder = Base64.getEncoder();
	private static Decoder decoder = Base64.getDecoder();

	/**
	 * 根据指定大小压缩图片(压缩到500k)
	 *
	 * @param imageBytes  源图片字节数组
	 * @param desFileSize 指定图片大小，单位kb
	 * @param imageId     影像编号
	 * @return 压缩质量后的图片字节数组
	 */
	public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
		if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
			return imageBytes;
		}
		long srcSize = imageBytes.length;

		int i = 0;

		try {
			while (imageBytes.length > desFileSize * 1024) {
				double accuracy = getAccuracy(imageBytes.length / 1024);							

				i++;
				
				log.info("压缩第" + i + "遍：压缩比：" + accuracy);

				ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);

				Thumbnails.of(inputStream).scale(accuracy).outputQuality(accuracy).toOutputStream(outputStream);
				imageBytes = outputStream.toByteArray();

				log.info("【图片压缩】 图片原大小={}kb | 压缩后大小={}kb", srcSize / 1024, imageBytes.length / 1024);
				
				if(i>=10) {
					break;
				}
			}
			
		} catch (Exception e) {
			log.error("【图片压缩】msg=图片压缩失败!", e);
		}
		return imageBytes;
	}
	
	/**
	 * 根据指定大小压缩图片(500k基础上再压缩到32k)
	 *
	 * @param imageBytes  源图片字节数组
	 * @param desFileSize 指定图片大小，单位kb
	 * @param
	 * @return 压缩质量后的图片字节数组
	 */
	public static byte[] compressPicForScale2(byte[] imageBytes, long desFileSize) {
		if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
			return imageBytes;
		}
		long srcSize = imageBytes.length;

		int i = 0;

		try {
			while (imageBytes.length > desFileSize * 1024) {
				double accuracy = getAccuracy2(imageBytes.length / 1024);							

				i++;
				
				log.info("压缩第" + i + "遍：压缩比：" + accuracy);

				ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);

				Thumbnails.of(inputStream).scale(accuracy).outputQuality(accuracy).toOutputStream(outputStream);
				imageBytes = outputStream.toByteArray();

				log.info("【图片压缩】 图片原大小={}kb | 压缩后大小={}kb", srcSize / 1024, imageBytes.length / 1024);
				
				if(i>=10) {
					break;
				}
			}
			
		} catch (Exception e) {
			log.error("【图片压缩】msg=图片压缩失败!", e);
		}
		return imageBytes;
	}

	/**
	 * 自动调节精度(经验数值)
	 *
	 * @param size 源图片大小
	 * @return 图片压缩质量比
	 */
	private static double getAccuracy(long size) {
		double accuracy;
		if (size < 600) {
			accuracy = 0.95;
		} else if (size < 900) {
			accuracy = 0.92;
		} else if (size < 1500) {
			accuracy = 0.87;
		} else if (size < 2700) {
			accuracy = 0.80;
		} else if (size < 4100) {
			accuracy = 0.69;
		} else if (size < 8900) {
			accuracy = 0.53;
		} else {
			accuracy = 0.26;
		}
		return accuracy;
	}
	
	
	/**
	 * 自动调节精度(经验数值)
	 *
	 * @param size 源图片大小
	 * @return 图片压缩质量比
	 */
	private static double getAccuracy2(long size) {
		double accuracy;
		if (size < 45) {
			accuracy = 0.93;
		} else if (size < 90) {
			accuracy = 0.81;
		} else if (size < 180) {
			accuracy = 0.69;
		} else if (size < 340) {
			accuracy = 0.57;
		} else {
			accuracy = 0.35;
		}
		return accuracy;
	}

	/**
	 * 图片压缩到指定大小
	 * 
	 * @param origPicContent
	 * @param desFileSize
	 * @return
	 */
	public static String compressPic(String origPicContent, Integer desFileSize) {

		byte[] bytes = decoder.decode(origPicContent);
		byte[] rtnBytes;
		
		if(desFileSize<50) {
		    //小于50k的先下所到500k,再压缩到30k
			rtnBytes = ImageUtils.compressPicForScale(bytes, 500);
			
			rtnBytes = ImageUtils.compressPicForScale2(rtnBytes, desFileSize);
		}else {
			rtnBytes = ImageUtils.compressPicForScale(bytes, desFileSize);
		}

		return encoder.encodeToString(rtnBytes);

	}

//	public static void main(String[] args) {
//		String imgFile = "g://image_svr/6.jpg";// 待处理的图片
//		InputStream in = null;
//		byte[] data = null;
//		// 读取图片字节数组
//		try {
//			in = new FileInputStream(imgFile);
//			data = new byte[in.available()];
//			in.read(data);
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			String imgStr = ImageUtils.compressPic(encoder.encodeToString(data), 32);
//
//			BufferedWriter out = new BufferedWriter(new FileWriter("g://111.txt"));
//			out.write(imgStr);
//			out.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}