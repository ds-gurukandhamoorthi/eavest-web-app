/**
 * 
 */
package com.synovia.digital.service;

import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 24 janv. 2017
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailClientTests {

	@Autowired
	private JavaMailSender defaultMailSender;

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.MailClient#MailClient(org.springframework.mail.javamail.JavaMailSender)}.
	 */
	/**
	 * 
	 */
	@Test
	public void testMailClient() {
		// Null entry
		try {
			new MailClient(null);

		} catch (Exception e) {
			fail("Should not have thrown an exception!");

		}

		// Nominal case
		try {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			new MailClient(mailSender);

		} catch (Exception e) {
			fail("Should not have thrown an exception!");

		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.MailClient#prepareAndSend(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testPrepareAndSend_withConfiguredMailSender() {
		// Test: Send a mail from a hand-made mail sender
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("synovia.digital@gmail.com");
		mailSender.setPassword("Lb0&JM6&Tc0");
		mailSender.setProtocol("smtp");
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.starttls.required", "true");
		mailSender.setJavaMailProperties(props);
		MailClient mailClient = new MailClient(mailSender);
		try {
			String recipient = "testmail@mailinator.com";
			mailClient.prepareAndSend(recipient, "Un message de test");

		} catch (Exception e) {
			fail("Should not have thrown an exception!");

		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.MailClient#prepareAndSend(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testPrepareAndSend_withAutoMailSender() {
		// Test: Send a mail from an auto-generated mail sender (that gets the application.properties)
		MailClient mailClient = new MailClient(defaultMailSender);
		try {
			String recipient = "testmail@mailinator.com";
			mailClient.prepareAndSend(recipient, "Un message de test");

		} catch (Exception e) {
			fail("Should not have thrown an exception!");

		}

	}

}
