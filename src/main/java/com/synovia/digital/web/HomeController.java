/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 29 déc. 2016
 */
@RestController
public class HomeController {

	private static final String VIEW_INDEX = "index";
	private static final String LOGIN_INDEX = "login";

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

	@RequestMapping(value = "/login")
	public ModelAndView login(ModelAndView modelAndView) {
		System.out.println("HomeController.login()");
		modelAndView.setViewName(LOGIN_INDEX);
		return modelAndView;
	}

}
