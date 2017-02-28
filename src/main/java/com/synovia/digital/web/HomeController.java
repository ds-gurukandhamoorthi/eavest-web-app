/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.service.EavAccountService;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 29 déc. 2016
 */
@RestController
public class HomeController {

	public static final String VIEW_INDEX = "index";
	public static final String VIEW_LOGIN = "login";
	public static final String VIEW_HOME = "home";

	@Autowired
	public EavAccountService accountService;

	@RequestMapping(value = "/basic")
	public String index() {
		System.out.println("HomeController.index()");
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView) {
		System.out.println("HomeController.index()");
		modelAndView.setViewName(VIEW_INDEX);
		return modelAndView;
	}

	//	@RequestMapping(value = "/", method = RequestMethod.GET)
	//	public String index(Model model) {
	//		System.out.println("HomeController.index()");
	//		return home(model);
	//	}

	@RequestMapping(value = "/login")
	public ModelAndView login(ModelAndView modelAndView) {
		System.out.println("HomeController.login()");
		modelAndView.setViewName(VIEW_LOGIN);
		return modelAndView;
	}

	@RequestMapping(value = "/home")
	public ModelAndView home(ModelAndView modelAndView) {
		System.out.println("HomeController.home()");
		modelAndView.setViewName(VIEW_HOME);
		String authentifiedUsername = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if (auth != null && principal instanceof User) {
			User u = (User) principal;
			String email = u.getUsername();

			EavAccount account = accountService.findByEmail(email);
			authentifiedUsername = getIdentifiedName(account);
		}
		modelAndView.addObject("authUserName", authentifiedUsername);
		return modelAndView;
	}

	private String getIdentifiedName(EavAccount account) {
		StringBuilder identifiedName = new StringBuilder("");
		if (account != null) {
			identifiedName.append(account.getFirstName().substring(0, 1));
			identifiedName.append(". ");
			identifiedName.append(account.getLastName());
		}
		return identifiedName.toString();
	}
}
