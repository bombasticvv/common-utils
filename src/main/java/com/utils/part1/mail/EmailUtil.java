package com.utils.part1.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 邮件发送工具类
 * 相关jar:mail-1.4.7.jar
 */
public class EmailUtil {
	
	private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP = "smtp";
    
    public static void main(String[] args) {
    	String filePath=System.getProperty("user.dir")+"\\resource\\mailTest.txt";
        List<File> fileList = new ArrayList<File>();
        fileList.add(new File(filePath));
        EmailUtil.sendMail("邮件SMTP服务器地址", "邮箱", "密码", "接收邮箱", "带附件的邮件测试aaa", "测试内容",
            fileList);
        System.out.println("end");
    }
    
    /**
     * 发送邮件
     * 
     * @param hostName
     *            邮件SMTP服务器地址
     * @param from
     *            发送者
     * @param fromPwd
     *            发送者密码
     * @param to
     *            接收者
     * @param subject
     *            邮件主题
     * @param content
     *            邮件内容
     * @param fileList
     *            附件列表
     * @throws Exception
     *             抛出异常
     */
    public static void sendMail(String hostName, String from, String fromPwd, String to, String subject, String content,
        List<File> fileList) {
        Properties props = new Properties();
        props.setProperty(MAIL_SMTP_HOST, hostName);
        props.put(MAIL_SMTP_AUTH, "true");
        Session session = Session.getDefaultInstance(props, null);
        // 用session对象来创建并初始化邮件对象
        MimeMessage mimeMsg = new MimeMessage(session);
        try {
            // 设置发送者
            mimeMsg.setFrom(new InternetAddress(from));
            // 设置接收者
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // 设置邮件主题
            mimeMsg.setSubject(subject);
            // 生成附件组件的实例
            MimeMultipart mp = new MimeMultipart();
            BodyPart bp = new MimeBodyPart();
            bp.setContent(content, CONTENT_TYPE);
            // 在组件上添加邮件文本
            mp.addBodyPart(bp);
            // 增加附件
            EmailUtil.addAttachment(mp, fileList);
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Transport transport = session.getTransport(MAIL_SMTP);
            // 连接邮件服务器并进行身份验证
            transport.connect(hostName, from, fromPwd);
            // 发送邮件
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addAttachment(MimeMultipart mp, List<File> fileList) {
        if (fileList==null||fileList.size()==0) {
            return;
        }
        try {
            for (File file : fileList) {
                BodyPart bp = new MimeBodyPart();
                FileDataSource fileds = new FileDataSource(file);
                bp.setDataHandler(new DataHandler(fileds));
                bp.setFileName(MimeUtility.encodeText(fileds.getName(), "utf-8", null)); // 解决附件名称乱码
                mp.addBodyPart(bp);// 添加附件
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
