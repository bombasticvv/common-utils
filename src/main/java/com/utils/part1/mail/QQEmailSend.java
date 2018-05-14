package com.utils.part1.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 使用QQ邮箱发送邮件
 * 首先需要先打开QQ邮箱，在设置-->账户中开启POP3/SMTP服务，并获取授权码
 * 参考:https://blog.csdn.net/qq422733429/article/details/51280020
 * 相关jar:mail-1.4.7.jar
 */
public class QQEmailSend {
	
	public static void main(String[] args) throws MessagingException {
		sendMail("XXXXXX@qq.com", "phinbmsudqwzbaib", "接收邮箱", "这是一封测试邮件", "这是测试邮件的内容");
		System.out.println("end");
	}
	
	public static void sendMail(String fromMail,String password,String toMail,String title,String content) throws MessagingException {
		// 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
        props.put("mail.smtp.port", "587");
        // 此处填写你的账号
        props.put("mail.user", fromMail);
        // 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", password);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress(toMail);
        message.setRecipient(RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject(title);
        // 设置邮件的内容体
        message.setContent(content, "text/html;charset=UTF-8");
        // 最后当然就是发送邮件啦
        Transport.send(message);
	}
	
}
