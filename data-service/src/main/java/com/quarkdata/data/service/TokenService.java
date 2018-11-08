package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

import java.util.Map;

/**
 * token Service
 *
 * @author  JiaLei
 */
public interface TokenService {

    /**
     * 验证 token
     *
     * @param token
     *        token
     * @return
     *        ResultCode
     */
    ResultCode<Map<String, Long>> validateToken(String token);
}
