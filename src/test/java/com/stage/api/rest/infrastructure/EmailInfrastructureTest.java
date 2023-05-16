package com.stage.api.rest.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.stage.SignOutRegistered;
import com.stage.SubscriptionRegistered;
import com.stage.api.rest.properties.EmailProperties;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeMessage.RecipientType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmailInfrastructure.class)
@EnableConfigurationProperties(value = EmailProperties.class)
@ActiveProfiles("test")
public class EmailInfrastructureTest {

	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private EmailInfrastructure emailInfrastructure;
	
	private GreenMail greenMail;

	@BeforeEach
	public void setup() {
		greenMail = new GreenMail(ServerSetupTest.SMTP);
		greenMail.setUser("username", "password");
		greenMail.start();
		emailInfrastructure = new EmailInfrastructure(emailProperties);
	}

	@AfterEach
	public void tearDown() {
		greenMail.stop();
	}
	
	@Test
	public void givenSubscribe_whenEmailIsSent_MessageIsReceived() throws Exception {

		SubscriptionRegistered sub = new SubscriptionRegistered();
        sub.setSubscriptionID("joran.vanbelle@gmail.comNieuwpoort1");
        sub.setLocation("Nieuwpoort");
        sub.setUsername("joran.vanbelle@gmail.com");
        sub.setTimestamp(1);

		emailInfrastructure.sendSubEmail("Nieuwpoort", "joran.vanbelle@gmail.com");

		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		assertEquals(1, receivedMessages.length);

		MimeMessage receivedMessage = receivedMessages[0];
		assertEquals("Subscribed to location Nieuwpoort", subjectFromMessage(receivedMessage));
		assertEquals(EmailInfrastructure.getSubText(sub.getLocation()), emailTextFrom(receivedMessage));
		assertEquals("joran.vanbelle@gmail.com", emailSentTo(receivedMessage));
	}
	
	@Test
	public void givenSignOut_whenEmailIsSent_MessageIsReceived() throws Exception {

		SignOutRegistered signout = new SignOutRegistered();
		signout.setSubscriptionID("joran.vanbelle@gmail.comNieuwpoort1");
		signout.setLocation("Nieuwpoort");
		signout.setUsername("joran.vanbelle@gmail.com");
		signout.setTimestamp(1);

		emailInfrastructure.sendSignOutEmail("Nieuwpoort", "joran.vanbelle@gmail.com");

		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		assertEquals(1, receivedMessages.length);

		MimeMessage receivedMessage = receivedMessages[0];
		assertEquals("Unsubscribed of location Nieuwpoort", subjectFromMessage(receivedMessage));
		assertEquals(EmailInfrastructure.getSignOutText(signout.getLocation()), emailTextFrom(receivedMessage));
		assertEquals("joran.vanbelle@gmail.com", emailSentTo(receivedMessage));
	}
	

	private static String subjectFromMessage(MimeMessage receivedMessage) throws MessagingException, MessagingException {
		return receivedMessage.getSubject();
	}

	private static String emailTextFrom(MimeMessage receivedMessage) throws IOException, MessagingException, IOException {
		return ((MimeMultipart) receivedMessage.getContent())
				.getBodyPart(0)
				.getContent()
				.toString();
	}	
	
	private static String emailSentTo(MimeMessage receivedMessage) throws IOException, MessagingException, IOException {
		return receivedMessage.getRecipients(RecipientType.TO)[0].toString();
	}
}
