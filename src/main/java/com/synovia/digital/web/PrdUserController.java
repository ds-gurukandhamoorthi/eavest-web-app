/**
 * 
 */
package com.synovia.digital.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdUserService;
import com.synovia.digital.utils.EavControllerUtils;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
@Controller
@RequestMapping(value = "/user")
public class PrdUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdUserController.class);

	public static final String VIEW_USER_PRODUCTS = "wallet";

	public static final String ATTR_PRD_USER = "prdUser";
	protected static final String ATTR_PRD_USER_PRODUCT_LIST = "userProducts";

	@Autowired
	private PrdUserService userService;

	@Autowired
	private PrdProductService productService;

	@GetMapping(value = "/products")
	public String userProducts(@RequestParam("account") EavAccount account, Model model) {
		String view = null;
		try {
			// Find the authenticated user and the corresponding PrdUser entity
			PrdUser prdUser = userService.getPrdUser(account);

			model.addAttribute(HomeController.ATTR_USERNAME_INFO, EavControllerUtils.getIdentifiedName(account));
			model.addAttribute(HomeController.ATTR_ACCOUNT, account);
			model.addAttribute(PrdUserController.ATTR_PRD_USER, prdUser);
			model.addAttribute(ATTR_PRD_USER_PRODUCT_LIST, productService.getUserProducts(prdUser));

			view = VIEW_USER_PRODUCTS;

		} catch (Exception e) {
			String errorMsg = "An error occurs while accessing the wallet for acconut {}";
			LOGGER.error(errorMsg, account);

			// TODO Process the error better
			e.printStackTrace();
			view = "error";

		}

		return view;
	}
}
