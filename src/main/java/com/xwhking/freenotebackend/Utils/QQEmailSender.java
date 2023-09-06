package com.xwhking.freenotebackend.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class QQEmailSender {
    public static void SendQQMail(String QQMailCode,String VerifyCode){
        // 发件人邮箱地址和授权码
        final String username = "************"; //自己的邮箱
        final String password = "************"; // 自己的邮箱授权码

        // 收件人邮箱地址
        String toEmail = QQMailCode;

        // 邮件主题和内容
        String subject = "注册验证码";
        String verificationCode = VerifyCode; // 生成验证码
        String validDuration = "30分钟"; // 设置验证码有效时间

        // 构建邮件内容
        String content = "尊敬的用户：\n\n";
        content += "您的注册验证码是：" + verificationCode + "\n";
        content += "请在注册页面输入此验证码以完成注册。\n";
        content += "此验证码将在 " + validDuration + " 后失效。\n\n";
        content += "祝您使用愉快！\n\n";
        content += "freenote";

        // 设置邮件属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");

        // 创建Session对象
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 创建MimeMessage对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            // 发送邮件
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Email sent Fail");
            e.printStackTrace();
        }
    }
}
