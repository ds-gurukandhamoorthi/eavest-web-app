/**
 * 
 */
package com.synovia.digital.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.service.EavAccountService;
import com.synovia.digital.service.PrdObservationDateService;
import com.synovia.digital.service.PrdProductService;

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

	protected static final String ATTR_REFUND_PRODUCT_LIST = "refundProducts";
	protected static final String ATTR_UPCOMING_PRODUCT_LIST = "tocallProducts";
	protected static final String ATTR_NB_PRODUCTS = "nbProducts";
	protected static final String ATTR_PRODUCT_NAME_LIST = "productNames";

	private static final int NB_DAYS_REFUND_PRODUCT_LIST = 30;

	@Autowired
	protected EavAccountService accountService;

	@Autowired
	protected PrdProductService productService;

	@Autowired
	protected PrdObservationDateService obsDateService;

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
		// Display user info
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

		// Retrieve the list of products
		List<PrdProduct> allProducts = productService.findAll();

		// Create a list of packed product names
		List<String> packedNames = productService.getPackedNameList(allProducts);
		modelAndView.addObject(ATTR_PRODUCT_NAME_LIST, packedNames);

		// Display the number of existing products
		int nbProducts = allProducts.size();
		modelAndView.addObject(ATTR_NB_PRODUCTS, nbProducts);

		// Display the list of refund products
		int nbDays = NB_DAYS_REFUND_PRODUCT_LIST;
		Date now = new Date();
		modelAndView.addObject(ATTR_REFUND_PRODUCT_LIST,
				productService.listRefundProducts(DateUtils.addDays(now, -nbDays)));
		// Display the list of upcoming products
		modelAndView.addObject(ATTR_UPCOMING_PRODUCT_LIST,
				productService.listUpcomingProducts(now, DateUtils.addDays(now, nbDays)));

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
