/**
 * 
 */
package com.synovia.digital.web;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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

import com.synovia.digital.dto.EavAccountDto;
import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.PrdProductListDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.service.EavAccountService;
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
	protected static final String ATTR_PRD_FEASE = "fease";
	protected static final String ATTR_PRD_TERMS = "terms";
	protected static final String ATTR_PRD_MARKET = "market";
	protected static final String ATTR_USER_UPCOMING_PRODUCTS = "userUpcomingProducts";
	protected static final String ATTR_USER_REFUND_PRODUCTS = "userRefundProducts";

	@Autowired
	private PrdUserService userService;

	@Autowired
	private EavAccountService accountService;

	@Autowired
	private PrdProductService productService;

	@Autowired
	protected PrdSousJacentService ssjctService;

	@GetMapping(value = "/products")
	public String showUserProducts(@RequestParam("account") EavAccount account, Model model, Principal principal,
			HttpSession session) {
		String view = null;
		try {
			String email = principal.getName();
			// Control the authenticated user
			if (!StringUtils.equals(account.getEmail(), email))
				return EavControllerUtils.createRedirectViewPath("/login");

			// Display user info
			if (session.getAttribute(HomeController.ATTR_USERNAME_INFO) == null) {
				session.setAttribute(HomeController.ATTR_USERNAME_INFO, EavControllerUtils.getIdentifiedName(account));
			}

			// Find the authenticated user and the corresponding PrdUser entity
			PrdUser prdUser = account.getPrdUser();

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

			// Retrieve the list of classic underlying assets
			model.addAttribute(ATTR_BASE_LIST, ssjctService.getClassicBases());

			// Retrieve the list of user upcoming products
			int nbDays = EavUtils.NB_DAYS_REFUND_PRODUCT_LIST;
			Date now = new Date();
			model.addAttribute(ATTR_USER_UPCOMING_PRODUCTS,
					productService.listUserUpcomingProducts(now, DateUtils.addDays(now, nbDays), prdUser));
			// Retrieve the list of user reimbursed products
			model.addAttribute(ATTR_USER_REFUND_PRODUCTS,
					productService.listUserRefundProducts(DateUtils.addDays(now, -nbDays), prdUser));

			view = VIEW_USER_PRODUCTS;

		} catch (Exception e) {
			String errorMsg = "An error occurs while accessing the wallet for account {}";
			LOGGER.error(errorMsg, account);

			e.printStackTrace();
			model.addAttribute(EavControllerUtils.ATTR_ERROR_RESPONSE, EavControllerUtils.I18N_ERROR_CODE);
			view = EavControllerUtils.VIEW_ERROR;

		}

		return view;
	}

	@PostMapping(value = "/{id}/updateInfo")
	public String updateAccount(@PathVariable Long id, @ModelAttribute("account") EavAccountDto accountDto, Model model,
			RedirectAttributes attributes) {
		String view;
		try {
			PrdUser u = userService.findById(id);
			LOGGER.info("Update info: {}", accountDto);
			EavAccount account = u.getAccount();
			accountService.update(account, accountDto);

			attributes.addAttribute(HomeController.ATTR_ACCOUNT, account);
			view = EavControllerUtils.createRedirectViewPath(HomeController.REQUEST_MAPPING_USER_PRODUCTS);

		} catch (EavEntryNotFoundException e) {
			e.printStackTrace();
			attributes.addFlashAttribute(EavControllerUtils.ATTR_ERROR_RESPONSE, EavControllerUtils.I18N_ERROR_CODE);
			view = EavControllerUtils.createRedirectViewPath(EavControllerUtils.REQUEST_MAPPING_ERROR);
		}
		return view;
	}

	@PostMapping(value = "/{id}/setProducts")
	public String setProducts(@ModelAttribute("selectedProducts") PrdProductListDto productList, @PathVariable Long id,
			Model model, RedirectAttributes attributes) {

		LOGGER.info("Selected products: {}", productList.getProductList());
		String view = null;
		try {
			// Set the product list
			Set<PrdProduct> products = new HashSet<>();
			for (PrdProductDto dto : productList.getProductList()) {
				if (dto.getId() != null) {
					products.add(productService.findById(dto.getId()));
				}
			}
			PrdUser u = userService.updateProductList(id, products);

			attributes.addAttribute(HomeController.ATTR_ACCOUNT, u.getAccount());

			view = EavControllerUtils.createRedirectViewPath(HomeController.REQUEST_MAPPING_USER_PRODUCTS);

		} catch (EavEntryNotFoundException e) {
			e.printStackTrace();
			attributes.addFlashAttribute(EavControllerUtils.ATTR_ERROR_RESPONSE, EavControllerUtils.I18N_ERROR_CODE);
			view = EavControllerUtils.createRedirectViewPath(EavControllerUtils.REQUEST_MAPPING_ERROR);
		}

		return view;
	}

	@RequestMapping(value = "/{id}/removeProduct")
	public String removeProduct(@RequestParam("prd") Long idPrdProduct, @PathVariable Long id, Model model,
			RedirectAttributes attributes) {
		String view = null;
		try {
			PrdProduct product = productService.findById(idPrdProduct);
			// Get the product list
			PrdUser u = userService.removeProduct(id, product);

			attributes.addAttribute(HomeController.ATTR_ACCOUNT, u.getAccount());

			view = EavControllerUtils.createRedirectViewPath(HomeController.REQUEST_MAPPING_USER_PRODUCTS);

		} catch (EavEntryNotFoundException e) {
			e.printStackTrace();
			attributes.addFlashAttribute(EavControllerUtils.ATTR_ERROR_RESPONSE, EavControllerUtils.I18N_ERROR_CODE);
			view = EavControllerUtils.createRedirectViewPath(EavControllerUtils.REQUEST_MAPPING_ERROR);
		}

		return view;
	}
}
