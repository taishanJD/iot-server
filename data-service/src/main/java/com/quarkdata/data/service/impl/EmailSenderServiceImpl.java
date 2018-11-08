package com.quarkdata.data.service.impl;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.EmailSenderService;
import com.quarkdata.data.util.EmailUtil;
import com.quarkdata.data.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 邮件发送服务
 *
 * @author  JiaLei
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    /**
     * 邮箱正则
     */
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // 邮件服务器
    @Value("${email.server}")
    private String emailServer;
    // 邮件管理员账号
    @Value("${email.manager.account}")
    private String emailManagerAccount;
    // 邮件管理员密码
    @Value("${email.manager.password}")
    private String emailManagerPassword;

    @Override
    public ResultCode sendEmail(String[] receiver, String emailSubject, String emailContent, String emailContentType) {
        ResultCode resultCode = new ResultCode();
        if (null == emailSubject || null == emailContent) {
            resultCode.setCode(Messages.EMAIL_CONTENT_ERROR_CODE);
            resultCode.setMsg(Messages.EMAIL_CONTENT_ERROR_MSG);
            return resultCode;
        }
        switch (emailContentType) {
            case "HTML" :
                emailContentType = EmailUtil.emailHTMLContentType;
                break;
            case "TEXT" :
                emailContentType = EmailUtil.emailTextContentType;
                break;
            default:
                resultCode.setCode(Messages.EMAIL_FORMAT_ERROR_CODE);
                resultCode.setMsg(Messages.EMAIL_FORMAT_ERROR_MSG);
                return resultCode;
        }
        if (receiver.length <= 0) {
            resultCode.setCode(Messages.EMAIL_RECEIVER_ERROR_CODE);
            resultCode.setMsg(Messages.EMAIL_RECEIVER_ERROR_MSG);
            return resultCode;
        }
        for (int i = 0; i < receiver.length; i++) {
            if(StringUtils.isEmpty(receiver[i]) || !checkEmail(receiver[i])) {
                resultCode.setCode(Messages.EMAIL_ACCOUNT_ERROR_CODE);
                resultCode.setMsg(Messages.EMAIL_ACCOUNT_ERROR_MSG);
                return resultCode;
            }
            receiver[i] = receiver[i].trim();
        }
        // 发送邮件
        EmailUtil.sendEmail(emailServer, emailManagerAccount, emailManagerPassword, emailManagerAccount,
                receiver, emailSubject, emailContent, emailContentType);
        return resultCode;
    }

    /**
     * 校验邮箱合法性
     */
    private static boolean checkEmail(String email){
        return Pattern.matches(EMAIL_REGEX, email.trim());
    }

}
