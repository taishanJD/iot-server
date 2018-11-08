package com.quarkdata.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Component
public class MailEngine {

    private static Logger logger = LoggerFactory.getLogger(MailEngine.class);

    private  MailSender mailSender;

    /*static ApplicationContext context = new
            ClassPathXmlApplicationContext("classpath:yunpan-apihub-beans-context-bean.xml");
    static MailSender mailSender=(MailSender)context.getBean("mailSender");*/

    public void sendMessage(String[] emailAddresses, String fromEmail, String bodyText, String subject,
                                   String attachmentName, ClassPathResource resource){
        //创建草稿
        MimeMessage mimeMessage = ((JavaMailSenderImpl) mailSender).createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailAddresses);
            if (fromEmail != null) {
                helper.setFrom(fromEmail);
            }
            helper.setText(bodyText);
            helper.setSubject(subject);

            if (attachmentName != null && resource != null)
                helper.addAttachment(attachmentName, resource);
            ((JavaMailSenderImpl) mailSender).send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
