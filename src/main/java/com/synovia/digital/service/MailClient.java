/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 24 janv. 2017
 */
@Service
public class MailClient {
	private JavaMailSender mailSender;

	@Autowired
	public MailClient(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void prepareAndSend(String recipient, String message) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("teddy.couriol@gmail.com");
			messageHelper.setTo(recipient);
			messageHelper.setSubject("Sample mail subject");
			messageHelper.setText(message);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
			e.printStackTrace();
			throw e;
		}
	}

}
