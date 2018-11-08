package com.quarkdata.data.service.impl;

import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.TokenService;
import com.quarkdata.data.util.JWTUtil;
import com.quarkdata.data.util.JedisUtils;
import com.quarkdata.data.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * token Service
 *
 * @author  JiaLei
 */
@Service
public class TokenServiceImpl implements TokenService {

    static Logger logger = Logger.getLogger(TokenServiceImpl.class);

    /**
     * 验证 token
     *
     * @param token
     *        token
     * @return
     *        ResultCode
     */
    @Override
    public ResultCode<Map<String, Long>> validateToken(String token) {
        ResultCode<Map<String, Long>> resultCode = JWTUtil.validateToken(token);
        // 验证 redis 中是否缓存了该 token
        if (resultCode.getCode().equals(Messages.SUCCESS_CODE)) {
            String tokenValue = JedisUtils.get(Constants.ONEIOT_REDIS_PREFIX
                                             + Constants.ONEIOT_REDIS_DELIMITER
                                             + Constants.ONEIOT_REDIS_TOKEN
                                             + Constants.ONEIOT_REDIS_DELIMITER
                                             + token);
            if (StringUtils.isEmpty(tokenValue)) {
                logger.warn(Messages.JWT_ERRCODE_TOKEN_INVALID_MSG + "，不是由服务端生成，密钥存在泄露风险");
                resultCode.setCode(Messages.JWT_ERRCODE_TOKEN_INVALID_CODE);
                resultCode.setMsg(Messages.JWT_ERRCODE_TOKEN_INVALID_MSG);
                resultCode.setData(null);
            }
        }
        return resultCode;
    }
}
