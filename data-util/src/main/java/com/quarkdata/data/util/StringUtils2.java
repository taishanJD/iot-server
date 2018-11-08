package com.quarkdata.data.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类, 继承com.quarkdata.yunpan.apihub.util.StringUtils类
 * @author jiadao
 * @date 2017-12-19
 */
public class StringUtils2 extends StringUtils {

	/**
	 * 一般用在批量操作，前端传递以逗号相隔的多个id组成的字符串，将其转换成Long型数组
	 */
	public static List<Long> convertStringParamToList(String paramStr){
		
		List<Long> docIdList = new ArrayList<>();
		if (isNotBlank(paramStr)) {
			String[] idStrs = paramStr.split(",");
			for (String idStr : idStrs) {
				if (isNotBlank(idStr)) {
					long docId = Long.valueOf(idStr);
					docIdList.add(docId);
				}
			}
			return docIdList;
		}
		return docIdList;
	}
}
