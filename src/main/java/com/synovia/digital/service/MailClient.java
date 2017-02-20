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
	private static final String MAIL_FROM = "synovia.digital@gmail.com";
	private static final String MAIL_SUBJECT_PREFIX = "EAVEST - ";
	private static final String MAIL_SUBJECT_RESET_PWD = MAIL_SUBJECT_PREFIX + "Reset your password";
	private static final String MAIL_SUBJECT_CONFIRM_MAIL = MAIL_SUBJECT_PREFIX + "Confirm your email address";
	private static final String MAIL_SUBJECT_ACCOUNT_INFO = MAIL_SUBJECT_PREFIX + "Account info";

	private JavaMailSender mailSender;

	@Autowired
	public MailClient(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void prepareAndSend(String recipient, String message) {
		prepareAndSend(MAIL_FROM, recipient, "Sample mail subject", message);
	}

	public void prepareAndSend(String from, String recipient, String subject, String message) {
		this.prepareAndSend(from, new String[] { recipient }, subject, message);

	}

	public void prepareAndSend(String from, String[] recipients, String subject, String message) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(from);
			messageHelper.setTo(recipients);
			messageHelper.setSubject(subject);
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

	/**
	 * Prepares and sends a mail for the reset password process.
	 * 
	 * @param recipient
	 *            The mail receiver.
	 * @param message
	 *            The message to send.
	 */
	public void prepareAndSendResetPassword(String recipient, String message) {
		prepareAndSend(MAIL_FROM, recipient, MAIL_SUBJECT_RESET_PWD, message);
	}

	/**
	 * Prepares and sends a mail for the confirm email process.
	 * 
	 * @param recipient
	 *            The mail receiver.
	 * @param message
	 *            The message to send.
	 */
	public void prepareAndSendConfirmEmail(String recipient, String message) {
		prepareAndSend(MAIL_FROM, recipient, MAIL_SUBJECT_CONFIRM_MAIL, message);
	}

	/**
	 * Prepares and sends a mail to inform administrator(s) of something concerning an
	 * account.
	 * 
	 * @param recipients
	 *            The mail receivers.
	 * @param message
	 *            The message to send.
	 */
	public void prepareAndSendInfo(String[] recipients, String message) {
		prepareAndSend(MAIL_FROM, recipients, MAIL_SUBJECT_ACCOUNT_INFO, message);

	}
}
