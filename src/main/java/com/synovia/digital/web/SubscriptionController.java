/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 19 janv. 2017
 */
@Controller
public class SubscriptionController {

	private static final String VIEW_SUBSCRIBE = "subscribe";
	private static final String VIEW_FORGET_PASSWORD = "forget_pwd";

	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(ModelAndView modelAndView) {
		System.out.println("SubscriptionController.subscribe()");
		modelAndView.setViewName("subscribe");
		return modelAndView;
	}

	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public ModelAndView createAccount(ModelAndView modelAndView) {
		System.out.println("SubscriptionController.createAccount()");
		modelAndView.setViewName(VIEW_SUBSCRIBE);
		return modelAndView;

	}

	@RequestMapping(value = "/forgotPassword")
	public String forgottenPassword() {
		System.out.println("SubscriptionController.forgottenPassword()");
		return "forget_pwd";
	}

	@RequestMapping(value = "/reinitPassword", method = RequestMethod.POST)
	public ModelAndView reinitPassword(ModelAndView modelAndView) {
		System.out.println("SubscriptionController.reinitPassword()");
		modelAndView.setViewName("info_pwd_sent");
		return modelAndView;
	}

}
