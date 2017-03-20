/**
 * 
 */
package com.synovia.digital.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.EavRole;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.repository.EavAccountRepository;
import com.synovia.digital.repository.EavRoleRepository;
import com.synovia.digital.repository.PrdUserRepository;
import com.synovia.digital.service.SubscriptionService;

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
	EavAccountRepository accountRepo;

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	EavRoleRepository roleRepo;

	//	@Autowired
	//	EavAccountRoleRepository accountRoleRepo;

	private static final String VIEW_SUBSCRIBE = "subscribe";
	private static final String VIEW_FORGET_PASSWORD = "forget-pwd";
	private static final String VIEW_ACCOUNT_CREATED = "account-created";

	@PostMapping(value = "/subscribe")
	public String createAccountSubmit(@ModelAttribute EavAccount account) {
		System.out.println("SubscriptionController.createAccountSubmit() - POST");
		// Assign roles as ROLE_USER (considering this way of creating an account as the way of creating users only, no admin) 
		Set<EavRole> roles = new HashSet<>();
		roles.add(roleRepo.findByLabel("ROLE_USER"));
		account.setEavRoles(roles);
		// Enable the account. May be disabled by admin later
		account.setEnabled(true);

		accountRepo.save(account);

		// Create the corresponding user entity linked to this account
		PrdUser user = new PrdUser(account);
		userRepo.save(user);

		// Send a e-mail to the new user
		subscriptionService.sendMailVerifyAccount(account.getEmail(), account);

		// Inform administrators
		//		List<EavAccount> admins = accountRoleRepo.findIdEavAccountByIdEavRole(1); // TODO Remove magic number.
		Set<EavAccount> admins = roleRepo.findByLabel("ROLE_ADMIN").getAccounts();
		List<String> adminMails = new ArrayList<>();
		for (EavAccount admin : admins) {
			adminMails.add(admin.getEmail());
		}
		subscriptionService.sendMailAccountCreated(adminMails.toArray(new String[0]), account);

		return VIEW_ACCOUNT_CREATED;

	}

	@GetMapping(value = "/subscribe")
	public String createAccountForm(Model model) {
		System.out.println("SubscriptionController.createAccountForm() - GET");
		model.addAttribute("eavAccount", new EavAccount());
		return VIEW_SUBSCRIBE;

	}

	@GetMapping(value = "/forgotPassword")
	public String forgottenPassword(Model model) {
		System.out.println("SubscriptionController.forgottenPassword()");
		model.addAttribute("forgottenCredentialsUser", new EavAccount());
		return VIEW_FORGET_PASSWORD;
	}

	@PostMapping(value = "/forgotPassword")
	public String reinitPassword(@ModelAttribute EavAccount forgottenCredentialsUser, Model model) {
		System.out.println("SubscriptionController.forgotPassword(POST)");
		model.addAttribute("forgottenCredentialsUser", new EavAccount());
		// Find existing account by e-mail
		EavAccount account = accountRepo.findByEmail(forgottenCredentialsUser.getEmail());

		if (account != null) {
			// Assign a new secured token
			String secureToken = UUID.randomUUID().toString();
			account.setResetPasswordToken(secureToken);

			// Give the token one hour expiration delay
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			Date expirationDate = new Date(calendar.getTimeInMillis());

			account.setResetPasswordExpires(expirationDate);

			// Commit eavAccount in database
			accountRepo.save(account);

			// Send an email to the user
			subscriptionService.sendMailResetPassword(forgottenCredentialsUser.getEmail(), secureToken);

			// Send attribute to the front-end
			String responseMessage = "Un mail été envoyé à votre adresse mail";
			model.addAttribute("responseMessage", responseMessage);

			return "info-pwd-sent";
		}
		// Deal with the case the user is not known
		String responseMessage = "Adresse mail inconnue. Ce compte n'existe pas";
		model.addAttribute("invalidMailMessage", responseMessage);

		return VIEW_FORGET_PASSWORD;
	}

}
