/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 févr. 2017
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	public JavaMailSender mailSender;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public SubscriptionServiceImpl() {
		// TODO Auto-generated constructor stub
		System.out.println("SubscriptionServiceImpl.SubscriptionServiceImpl()");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.SubscriptionService#sendMailVerifyAccount(java.lang.
	 * String, com.synovia.digital.domain.EavAccount)
	 */
	@Override
	public void sendMailVerifyAccount(String to, EavAccount account) {
		System.out.println("SubscriptionServiceImpl.sendMailVerifyAccount()");
		StringBuilder bodyMsg = new StringBuilder("Dear ");
		bodyMsg.append(account.getFirstName()).append(" ").append(account.getLastName()).append("\n").append("\n");
		bodyMsg.append(
				"This message is auto-generated to confirm your inscription to EAVEST. Please click the link below")
				.append("\n");
		bodyMsg.append("http://localhost:8080/login").append("\n").append("\n");
		bodyMsg.append("Best regards.");

		MailClient mailClient = new MailClient(mailSender);
		mailClient.prepareAndSendConfirmEmail(to, bodyMsg.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.SubscriptionService#sendMailAccountCreated(java.lang.
	 * String, com.synovia.digital.domain.EavAccount)
	 */
	@Override
	public void sendMailAccountCreated(String[] to, EavAccount account) {
		System.out.println("SubscriptionServiceImpl.sendMailAccountCreated()");
		StringBuilder bodyMsg = new StringBuilder("Dear admin,");
		bodyMsg.append("\n").append("\n");
		bodyMsg.append("A new account has been created in EAVEST web site (").append(account.getLastName()).append(" ")
				.append(account.getFirstName()).append(" mail:").append(account.getEmail()).append(").\n");
		bodyMsg.append("You can click the link below to manage this account").append("\n");
		bodyMsg.append("http://localhost:8080/admin").append("\n").append("\n");
		bodyMsg.append("Best regards.");

		MailClient mailClient = new MailClient(mailSender);
		mailClient.prepareAndSendInfo(to, bodyMsg.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.SubscriptionService#sendMailResetPassword(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void sendMailResetPassword(String to, String secureToken) {
		System.out.println("SubscriptionServiceImpl.sendMailResetPassword()");
		StringBuilder bodyMsg = new StringBuilder("Dear User").append("\n").append("\n");
		bodyMsg.append("Please click the link below to configure your password settings.").append("\n");
		bodyMsg.append(ServletUriComponentsBuilder.fromCurrentContextPath().path("/reinitPassword")
				.queryParam("_key", secureToken).build().toUriString()).append("\n").append("\n");
		bodyMsg.append(
				"If you did not request this, please ignore this email and your password will remain unchanged.\n\n");
		bodyMsg.append("Best regards.");

		MailClient mailClient = new MailClient(mailSender);
		mailClient.prepareAndSendResetPassword(to, bodyMsg.toString());

	}

}
