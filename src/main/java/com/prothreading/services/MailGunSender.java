package com.prothreading.services;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;


public class MailGunSender {
	
	private String fromEmail;
	private String fullName;
	private String subject;
	private String body;
	
	
	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}


	public String send() throws AddressException, MessagingException
	{
		Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.mailgun.org");
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));

        InternetAddress[] addrs = InternetAddress.parse("info@prothreading.com", false);
        msg.setRecipients(Message.RecipientType.TO, addrs);

        msg.setSubject(subject);
        msg.setText(body);
        msg.setSentDate(new Date());

        SMTPTransport t =
            (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.mailgun.com", "info@prothreading.com", "Brisha0331");
        t.sendMessage(msg, msg.getAllRecipients());
        
        String returnMessage = t.getLastServerResponse();
        
        t.close();
        
        return returnMessage;
	}
	
}
