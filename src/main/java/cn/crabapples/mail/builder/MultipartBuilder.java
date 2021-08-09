package cn.crabapples.mail.builder;


import ch.ethz.ssh2.crypto.Base64;
import cn.crabapples.mail.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 建造者模式-建造器(实现类)
 *
 * @author Mr.He
 * 2020/7/13 13:29
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class MultipartBuilder implements Builder {
    private static final Logger logger = LoggerFactory.getLogger(MultipartBuilder.class);
    private final List<BodyPart> bodyParts;
    private final Multipart multipart;

    public MultipartBuilder() {
        bodyParts = new ArrayList<>();
        multipart = new MimeMultipart();
    }

    @Override
    public Multipart build() {
        if (bodyParts.size() <= 0) {
            throw new ApplicationException("邮件内容不能为空");
        }
        bodyParts.forEach(e -> {
            try {
                multipart.addBodyPart(e);
            } catch (MessagingException messagingException) {
                throw new ApplicationException("构建邮件消息时出现异常!", messagingException);
            }
        });
        return multipart;
    }

    @Override
    public MultipartBuilder addContent(String contentText) {
        try {
            logger.debug("设置邮件正文:[{}]", contentText);
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(contentText);
            bodyParts.add(bodyPart);
            return this;
        } catch (MessagingException e) {
            throw new ApplicationException("设置邮件正文出现异常!", e);
        }
    }

    @Override
    public MultipartBuilder addContentFile(String filePath, String fileName) {
        try {
            logger.debug("设置邮件附件:[{}]", filePath);
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setDataHandler(new DataHandler(new FileDataSource(filePath)));
            bodyPart.setFileName("=?UTF-8?B?" + new String(Base64.encode(fileName.getBytes())) + "?=");
            bodyParts.add(bodyPart);
            return this;
        } catch (MessagingException e) {
            throw new ApplicationException("添加邮件附件时出现异常!", e);
        }

    }
}
