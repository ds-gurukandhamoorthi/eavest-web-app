/**
 * 
 */
package com.synovia.digital.web;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.PrdProductListDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdSousJacentService;
import com.synovia.digital.service.PrdUserService;
import com.synovia.digital.utils.EavControllerUtils;
import com.synovia.digital.utils.EavUtils;
import com.synovia.digital.utils.PerfReviewDates;

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
	protected static final String ATTR_BASE_LIST = "classicBases";
	protected static final String ATTR_LAST_MONTH = "month";
	protected static final String ATTR_CURRENT_YEAR = "year";
	protected static final String ATTR_ONE_YEAR_BEFORE = "oneYearPast";

	@Autowired
	private PrdUserService userService;

	@Autowired
	private PrdProductService productService;

	@Autowired
	protected PrdSousJacentService ssjctService;

	@GetMapping(value = "/products")
	public String userProducts(@RequestParam("account") EavAccount account, Model model) {
		String view = null;
		try {
			// Find the authenticated user and the corresponding PrdUser entity
			PrdUser prdUser = userService.getPrdUser(account);
			LOGGER.info("The current PrdUser is {}", prdUser);

			model.addAttribute(HomeController.ATTR_USERNAME_INFO, EavControllerUtils.getIdentifiedName(account));
			model.addAttribute(HomeController.ATTR_ACCOUNT, account);
			// Display the information of the current user
			model.addAttribute(PrdUserController.ATTR_PRD_USER, prdUser);
			// Retrieve the list of user products
			List<PrdProduct> userProducts = productService.getUserProducts(prdUser);
			model.addAttribute(ATTR_PRD_USER_PRODUCT_LIST, userProducts.isEmpty() ? null : userProducts);
			// Display the current date
			PerfReviewDates prd = new PerfReviewDates();
			model.addAttribute(ATTR_LAST_MONTH,
					prd.getLastDayOfLastMonthAsString(new SimpleDateFormat(EavUtils.PRD_MONTH_FORMAT_PATTERN)));
			model.addAttribute(ATTR_CURRENT_YEAR,
					prd.getLastDayOfLastMonthAsString(new SimpleDateFormat(EavUtils.PRD_YEAR_FORMAT_PATTERN)));
			model.addAttribute(ATTR_ONE_YEAR_BEFORE, prd
					.getLastDayOfLastMonthAsString(new SimpleDateFormat(EavUtils.PRD_DAY_MONTH_YEAR_FORMAT_PATTERN)));

			// Retrieve the list of classic bases
			model.addAttribute(ATTR_BASE_LIST, ssjctService.getClassicBases());

			view = VIEW_USER_PRODUCTS;

		} catch (Exception e) {
			String errorMsg = "An error occurs while accessing the wallet for account {}";
			LOGGER.error(errorMsg, account);

			// TODO Process the error better
			e.printStackTrace();
			view = "error";

		}

		return view;
	}

	@PostMapping(value = "/{id}/addProducts")
	public String addProducts(@ModelAttribute("selectedProducts") PrdProductListDto productList, @PathVariable Long id,
			Model model, RedirectAttributes attributes) {

		LOGGER.info("Selected products: {}", productList.getProductList());
		String view = null;
		try {
			// Set the product list
			Set<PrdProduct> products = new HashSet<>();
			for (PrdProductDto dto : productList.getProductList()) {
				products.add(productService.findById(dto.getId()));
			}
			PrdUser u = userService.updateProductList(id, products);

			attributes.addAttribute(HomeController.ATTR_ACCOUNT, u.getAccount());

			view = EavControllerUtils.createRedirectViewPath(HomeController.REQUEST_MAPPING_USER_PRODUCTS);

		} catch (EavEntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}

}
