package com.drondea.wireless.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {
	private String host = ""; // smtp服务器
	private String from = ""; // 发件人地址
	private String to = ""; // 收件人地址
	private String affix = ""; // 附件地址
	private String affixName = ""; // 附件名称
	private String user = ""; // 用户名
	private String pwd = ""; // 密码
	private String subject = ""; // 邮件标题
	private String content = "";// 邮件内容

	public void setAddress(String from, String to, String subject, String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public void setAffix(String affix, String affixName) {
		this.affix = affix;
		this.affixName = affixName;
	}

	// 阿里云不支持25端口，需要用ssl,465端口
	public void send(String host, String port, String user, String pwd) throws Exception {
		this.host = host;
		this.user = user;
		this.pwd = pwd;

		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);//主机名
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.port", port);// 端口号
		props.setProperty("mail.transport.protocol", "smtp");// 连接协议
//		MyAuthenricator authenticator = new MyAuthenricator(this.user, this.pwd);
		// 用刚刚设置好的props对象构建一个session
		 Session session = Session.getDefaultInstance(props);
//		Session session = Session.getInstance(props, authenticator);

		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
//		session.setDebug(true);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
			// 加载发件人地址
			message.setFrom(new InternetAddress(from));
			// 加载收件人地址
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// 加载标题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);
			// 添加附件
			// BodyPart messageBodyPart = new MimeBodyPart();
			// DataSource source = new FileDataSource(affix);
			// 添加附件的内容
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// 添加附件的标题
			// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
			// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			// messageBodyPart.setFileName("=?GBK?B?"
			// + enc.encode(affixName.getBytes()) + "?=");
			// multipart.addBodyPart(messageBodyPart);

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
	}

	static class MyAuthenricator extends Authenticator {
		String user = null;
		String pass = "";

		public MyAuthenricator(String user, String pass) {
			this.user = user;
			this.pass = pass;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pass);
		}

	}
	
	  public static void main(String[] args) throws Exception { 
	      MailUtils cn = new MailUtils();
	      // 设置发件人地址、收件人地址和邮件标题 、内容
	      cn.setAddress("drondea@126.com", "99002548@qq.com", "test", "wahaha");
	      // 设置要发送附件的位置和标题 //
//	      cn.setAffix("f:/123.txt", "123.txt");
//	      cn.send("smtp.drondea.com", "465", "postmaster@drondea.com", "Java283066589");
	      cn.send("smtp.126.com", "465", "drondea@126.com", "DRONDEA20191110");
	  }
	 
}