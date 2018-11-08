package com.quarkdata.data.util.common.preprocess;

import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_101;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_102;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_103;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_104;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_105;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_106;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_107;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_108;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_201;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_202;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_203;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_204;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_205;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_206;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_207;
import com.quarkdata.data.util.common.preprocess.factoryImpl.OperPreprocess_208;

/**
 * 预处理步骤工场
 * 
 * @author WangHao 2018年7月4日
 */
public class PreporocessFactory {

	public static OperPreprocess createOperPreprocess(Integer stepId) {
		OperPreprocess operPreprocess = null;
		switch (stepId) {
		case 101:
			operPreprocess = new OperPreprocess_101();
			break;
		case 102:
			operPreprocess = new OperPreprocess_102();
			break;
		case 103:
			operPreprocess = new OperPreprocess_103();
			break;
		case 104:
			operPreprocess = new OperPreprocess_104();
			break;
		case 105:
			operPreprocess = new OperPreprocess_105();
			break;
		case 106:
			operPreprocess = new OperPreprocess_106();
			break;
		case 107:
			operPreprocess = new OperPreprocess_107();
			break;
		case 108:
			operPreprocess = new OperPreprocess_108();
			break;
		case 201:
			operPreprocess = new OperPreprocess_201();
			break;
		case 202:
			operPreprocess = new OperPreprocess_202();
			break;
		case 203:
			operPreprocess = new OperPreprocess_203();
			break;
		case 204:
			operPreprocess = new OperPreprocess_204();
			break;
		case 205:
			operPreprocess = new OperPreprocess_205();
			break;
		case 206:
			operPreprocess = new OperPreprocess_206();
			break;
		case 207:
			operPreprocess = new OperPreprocess_207();
			break;
		case 208:
			operPreprocess = new OperPreprocess_208();
			break;
		default:
			break;
		}
		return operPreprocess;
	}
}
