package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

/**
 * 邮件服务, 依赖工具类 com.quarkdata.tenant.util.EmailUtil
 */
public interface EmailSenderService {

   /**
    * 发送邮件
    *
    * @param receiver
    *        邮件接收人（可以群发）
    * @param emailSubject
    *        邮件标题
    * @param emailContent
    *        邮件内容
    * @param emailContentType
    *         邮件格式（ "HTML" 或者 "TEXT" ）
    */
   ResultCode sendEmail(String[] receiver, String emailSubject, String emailContent, String emailContentType);

}
