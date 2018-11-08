package com.quarkdata.data.service.impl;

import com.quarkdata.data.service.TestService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TestServiceImpl implements TestService {

	static Logger logger = Logger.getLogger(TestServiceImpl.class);


	@Override
	public void getTest(String out_trade_no, String trade_no, String trade_status) {
		logger.info("test service");
	}




}
