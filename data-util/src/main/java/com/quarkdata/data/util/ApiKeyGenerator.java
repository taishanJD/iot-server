package com.quarkdata.data.util;

import java.util.Random;

/**
 * 生成APIKey工具类
 * @author luohl
 * 
 */
public class ApiKeyGenerator {
	
	 public static String getApiKey(int length){  
	        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
	        Random random = new Random();  
	        StringBuffer sb = new StringBuffer();  
	        for(int i = 0 ; i < length; ++i){  
	            int number = random.nextInt(62);  
	            sb.append(str.charAt(number));  
	        }  
	        return sb.toString();  
	    }  
}
