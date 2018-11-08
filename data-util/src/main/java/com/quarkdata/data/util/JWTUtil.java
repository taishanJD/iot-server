package com.quarkdata.data.util;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * @author wujianbo
 */
public class JWTUtil {

    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    private static final String USER_ID = "userId";
    private static final String TEN_ID = "tenId";

    private static String JWT_ID = PropertiesUtils.prop.get("jwt.id");
    private static String JWT_SECRET = PropertiesUtils.prop.get("jwt.secret");
    private static Integer JWT_TTL = Integer.parseInt(PropertiesUtils.prop.get("jwt.ttl"));
    private static String JWT_SUBJECT = PropertiesUtils.prop.get("jwt.subject");
    private static String JWT_ISSUER = PropertiesUtils.prop.get("jwt.issuer");

    /*public static String JWT_ID = "jwt";
    public static String JWT_SECRET = "xasdasxzcascascaswwdaw";
    public static Integer JWT_TTL = 86400000;
    public static String JWT_SUBJECT = "oneshare";
    public static String JWT_ISSUER = "quarkdata";*/


    /**
     * 生成token
     * @return
     */
    public static String createToken(long userId, long tenId){
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID, userId);
        map.put(TEN_ID, tenId);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(JWT_ID)                                      // JWT_ID
                //.setAudience("")                                  // 接受者
                .setClaims(map)                                     // 自定义属性
                .setSubject(JWT_SUBJECT)                            // 主题
                .setIssuer(JWT_ISSUER)                              // 签发者
                .setIssuedAt(now)                                   // 签发时间
                .setNotBefore(now)                                  // 失效时间
                .signWith(signatureAlgorithm, JWT_SECRET);          // 签名算法以及密匙

        if (JWT_TTL >= 0) {
            long expMillis = nowMillis + JWT_TTL;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static ResultCode<Map<String, Long>> validateToken(String token){
        ResultCode<Map<String, Long>> resultCode = new ResultCode<>();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            Map<String, Long> map = new HashMap<>();
            map.put(TEN_ID,  Long.parseLong(claims.get(TEN_ID)+""));
            map.put(USER_ID, Long.parseLong(claims.get(USER_ID)+""));
            resultCode.setData(map);
        } catch (ExpiredJwtException e) {
            logger.error(Messages.JWT_ERRCODE_EXPIRE_MSG, e);
            resultCode.setCode(Messages.JWT_ERRCODE_EXPIRE_CODE);
            resultCode.setMsg(Messages.JWT_ERRCODE_EXPIRE_MSG);
        } catch (SignatureException e) {
            logger.error(Messages.JWT_ERRCODE_SIGN_MSG, e);
            resultCode.setCode(Messages.JWT_ERRCODE_SIGN_CODE);
            resultCode.setMsg(Messages.JWT_ERRCODE_SIGN_MSG);
        } catch (Exception e) {
            logger.error(Messages.JWT_ERRCODE_FAIL_MSG, e);
            resultCode.setCode(Messages.JWT_ERRCODE_FAIL_CODE);
            resultCode.setMsg(Messages.JWT_ERRCODE_FAIL_MSG);
        }
        return resultCode;
    }

    public static void main(String[] args) {
        String token = JWTUtil.createToken(1, 2);
        System.out.println(token);
    }

}
