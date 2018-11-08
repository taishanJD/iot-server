package com.quarkdata.data.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密工具类
 *
 * @author JiaLei
 */
public class AESEncryptUtil {

    private static Logger logger = Logger.getLogger(AESEncryptUtil.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM = "SHA1PRNG";
    // 默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     *
     * @param content
     *        明文内容
     * @param password
     *        明文密码
     * @return
     *        返回Base64转码后的密文
     */
    public static String encrypt(String content, String password) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes("utf-8");
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
            // 加密明文
            byte[] result = cipher.doFinal(byteContent);
            // 通过Base64转码返回
            return Base64.encodeBase64String(result);
        } catch (Exception ex) {
            logger.error("AES加密时出现问题！Exception: " + ex.getMessage());
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content
     *        密文
     * @param password
     *        明文密钥
     * @return
     *        明文
     */
    public static String decrypt(String content, String password) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            // 使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            // 执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "utf-8");
        } catch (Exception ex) {
            logger.error("AES验证密文身份出现问题！Exception: " + ex.getMessage());
            return null;
        }
    }

    /**
     * 生成加密秘钥
     *
     * @param password
     *        明文密钥
     * @return
     *        加密密钥
     */
    private static SecretKeySpec getSecretKey(final String password) {
        try {
            //返回生成指定算法密钥生成器的 KeyGenerator 对象
            KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance(ALGORITHM);
            random.setSeed(password.getBytes());
            keyGen.init(128, random);
            SecretKey skey = keyGen.generateKey();
            byte[] raw = skey.getEncoded();
            return new SecretKeySpec(raw, KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("AES加密密钥时出现问题！Exception：" + ex.getMessage());
        }
        return null;
    }
}