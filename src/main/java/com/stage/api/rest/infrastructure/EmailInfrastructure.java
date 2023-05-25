package com.stage.api.rest.infrastructure;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.stage.api.rest.properties.EmailProperties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class EmailInfrastructure {
	
	private final Properties prop;
	private final EmailProperties emailProperties;

	public EmailInfrastructure(EmailProperties emailProperties) {
		
		this.emailProperties = emailProperties;
		
		prop = new Properties();
//		prop.put("mail.smtp.auth", true);
//		prop.put("mail.smtp.starttls.enable", "true");
//		prop.put("mail.smtp.host", emailProperties.getHost());
//		prop.put("mail.smtp.port", emailProperties.getPort());
//		prop.put("mail.smtp.ssl.trust", emailProperties.getHost());

		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", emailProperties.getHost());
		prop.put("mail.smtp.port", emailProperties.getPort());
	}

	public void sendSubEmail(String location, String email) {

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
			}
		});

		Message message = new MimeMessage(session);
		Multipart multipart = new MimeMultipart();
		try {
			message.setFrom(new InternetAddress("joran.vanbelle2@student.hogent.be"));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(getSubText(location), "text/html; charset=utf-8");
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);
			message.setSubject(String.format("Subscribed to location %s", location));

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendSignOutEmail(String location, String email) {

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
			}
		});

		Message message = new MimeMessage(session);
		Multipart multipart = new MimeMultipart();
		try {
			message.setFrom(new InternetAddress("joran.vanbelle2@student.hogent.be"));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(getSignOutText(location), "text/html; charset=utf-8");
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);
			message.setSubject(String.format("Unsubscribed of location %s", location));

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getSubText(String location) {
		return String.format("You are subscribed to %s.%nYou will now receive emails every time the weather is kiteable here. "
				+ "If you don't want to receive any emails anymore, you can either unsubscribe through the app or when you receive an email about the weather.", location);
	}
	
	public static String getSignOutText(String location) {
		return String.format("You won't receive any emails of %s anymore.\r\nIf you want to receive email again, you can subscribe again inside the app.", location);
	}
}

