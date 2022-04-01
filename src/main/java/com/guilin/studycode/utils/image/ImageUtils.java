package com.guilin.studycode.utils.image;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.UUID;

public class ImageUtils {

	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);

	private static Encoder encoder = Base64.getEncoder();
	private static Decoder decoder = Base64.getDecoder();

	/**
	 * 根据指定大小压缩图片(压缩到500k)
	 *
	 * @param imageBytes  源图片字节数组
	 * @param desFileSize 指定图片大小，单位kb
	 * @param //     影像编号
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


	//根据图片地址从互联网下载图片到指定电脑指定位置
	private static void downlodImg(String imgUrl,File dir ) {

		File file = null;
		FileOutputStream out = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;

		// 如果保存图片的文件夹不存在，那就创建
		if (!dir.exists()) {
			// mkdir()只能创建1级文件夹，而mkdirs()可以创建路径中出现的所有文件夹
			dir.mkdirs();
		}
		try {
			// 第一个参数是图片存放的路径，第二个参数是图片的名称，这里我采用UUID生成图片名称
			file = new File(dir, UUID.randomUUID().toString().replace("-", "") + ".jpg");
			// 构造一个URL对象
			URL url = new URL(imgUrl);
			// 获取URLConnection对象
			conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式，默认是GET
			conn.setRequestMethod("GET");
			// 限制输入流等待数据到达的时间，超时将会抛出java.net.SocketTimeoutException
			conn.setReadTimeout(3000);
			// 限制socket等待建立连接的时间，超时将会抛出java.net.SocketTimeoutException
			conn.setConnectTimeout(3000);
			// 获取输入流
			inputStream = conn.getInputStream();
			// 以流的方式输出图片
			out = new FileOutputStream(file);
			byte[] arr = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(arr)) != -1) {
				out.write(arr, 0, len);
			}
			out.flush();
			System.out.println("提醒：图片下载成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 回收资源
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//根据图片地址从互联网下载图片到指定电脑指定位置
	public static void main(String[] args) {
		// 下载图片的地址
		String imgUrl = "https://bkimg.cdn.bcebos.com/pic/55e736d12f2eb938e8824ef3da628535e4dd6fc7?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto";
		// 设置保存图片的文件夹路径
		File dir = new File("E:/fileUp/test/img/" + new SimpleDateFormat("yyyy/MM-dd/HH").format(new Date()));
		downlodImg(imgUrl,dir);
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