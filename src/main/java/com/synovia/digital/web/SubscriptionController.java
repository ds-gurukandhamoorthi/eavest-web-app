/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.synovia.digital.domain.PrdUser;
import com.synovia.digital.repository.PrdUserRepository;
import com.synovia.digital.service.MailClient;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 19 janv. 2017
 */
@Controller
public class SubscriptionController {

	@Autowired
	PrdUserRepository userRepo;

	@Autowired
	JavaMailSender mailSender;

	private static final String VIEW_SUBSCRIBE = "subscribe";
	private static final String VIEW_FORGET_PASSWORD = "forget_pwd";

	@PostMapping(value = "/subscribe")
	public String createAccountSubmit(@ModelAttribute PrdUser prdUser) {
		System.out.println("SubscriptionController.createAccount() - POST");
		userRepo.save(prdUser);
		StringBuilder bodyMsg = new StringBuilder("Dear ");
		bodyMsg.append(prdUser.getFirstName()).append(" ").append(prdUser.getLastName()).append("\n").append("\n");
		bodyMsg.append(
				"This message is auto-generated to confirm your inscription to EAVEST. Please click the link below")
				.append("\n");
		bodyMsg.append("http://localhost:8080/login").append("\n").append("\n");
		bodyMsg.append("Best regards.");

		MailClient mailClient = new MailClient(mailSender);
		mailClient.prepareAndSend(prdUser.getEmail(), bodyMsg.toString());
		return "account_created";

	}

	@GetMapping(value = "/subscribe")
	public String createAccountForm(Model model) {
		System.out.println("SubscriptionController.createAccount() - GET");
		model.addAttribute("prdUser", new PrdUser());
		return VIEW_SUBSCRIBE;

	}

	@GetMapping(value = "/forgotPassword")
	public String forgottenPassword(Model model) {
		System.out.println("SubscriptionController.forgottenPassword()");
		model.addAttribute("forgottenCredentialsUser", new PrdUser());
		return VIEW_FORGET_PASSWORD;
	}

	@PostMapping(value = "/forgotPassword")
	public String reinitPassword(@ModelAttribute PrdUser forgottenCredentialsUser) {
		System.out.println("SubscriptionController.forgotPassword(POST)");
		// TODO Find prdUser by e-mail

		// TODO Deal with the case the user is not known

		StringBuilder bodyMsg = new StringBuilder("Dear User").append("\n").append("\n");
		bodyMsg.append("Please click the link below to configure your password settings.").append("\n");
		bodyMsg.append("http://localhost:8080/reinitPassword").append("\n").append("\n");
		bodyMsg.append("Best regards.");

		MailClient mailClient = new MailClient(mailSender);
		mailClient.prepareAndSend(forgottenCredentialsUser.getEmail(), bodyMsg.toString());
		return "info_pwd_sent";
	}

}
