package com.quarkdata.data.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 * 
 * @author typ 2017年12月21日
 */
public class FileUtils {

	/**
	 * 获取文件的md5值
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String getMd5Hex(InputStream inputStream) throws IOException {
		String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
		return md5;
	}
	
	/**
	 * 文件大小转化为易读的格式(KB、MB、GB)
	 * @param size 
	 * @return	
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte(s)";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
}
