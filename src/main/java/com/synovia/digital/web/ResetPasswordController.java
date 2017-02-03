/**
 * 
 */
package com.synovia.digital.web;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.synovia.digital.domain.EavAccount;
import com.synovia.digital.repository.EavAccountRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 30 janv. 2017
 */
@Controller
public class ResetPasswordController {
	@Autowired
	private EavAccountRepository repo;

	@GetMapping(value = "/reinitPassword")
	public String reinitPasswordView(@RequestParam(value = "_key") String resetToken, Model model) {
		System.out.println("ResetPasswordController.reinitPasswordView()");
		EavAccount account = repo.findByResetPasswordToken(resetToken);
		if (account != null) {
			Date expirationDate = account.getResetPasswordExpires();
			if (expirationDate.before(new java.util.Date())) {
				model.addAttribute("usrAccount", account);
				model.addAttribute("resetPasswordToken", resetToken);
				return "reinit-password";
			}
		}
		return "reinit-error";
	}

	@PostMapping(value = "/reinitPassword")
	public String reinitPassword(@ModelAttribute EavAccount usrAccount,
			@RequestParam(value = "_key") String resetPasswordToken, Model model) {
		System.out.println("ResetPasswordController.reinitPassword()");
		model.addAttribute("usrAccount", usrAccount);
		EavAccount accountToUpdate = repo.findByResetPasswordToken(resetPasswordToken);
		String updatedPassword = usrAccount.getPassword();
		accountToUpdate.setEncryptedPassword(updatedPassword);
		accountToUpdate.setResetPasswordToken(null);
		accountToUpdate.setResetPasswordExpires(null);

		repo.save(accountToUpdate);

		// Send back information to display in the front-end
		boolean passwordChanged = true;
		String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();
		String responseMessage = "Le mot de passe a été réinitialisé";

		model.addAttribute("passwordChanged", passwordChanged);
		model.addAttribute("redirectionUrl", redirectionUrl);
		model.addAttribute("responseMessage", responseMessage);

		return "reinit-password";
	}
}
