/**
 * 
 */
package com.synovia.digital.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.service.EavAccountService;
import com.synovia.digital.service.EavParamsService;
import com.synovia.digital.service.PrdObservationDateService;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdSousJacentService;
import com.synovia.digital.utils.EavControllerUtils;
import com.synovia.digital.utils.EavUtils;
import com.synovia.digital.utils.PerfReviewDates;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 29 déc. 2016
 */
@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	public static final String STATIC_RESOURCES_DIR = "src/main/resources/static/";
	public static final String IMG_DIR_BEST_SELLER = "img/bestseller/";

	public static final String VIEW_INDEX = "index";
	public static final String VIEW_LOGIN = "login";
	public static final String VIEW_HOME = "home";
	public static final String VIEW_WALLET = "wallet";

	protected static final String ATTR_REFUND_PRODUCT_LIST = "refundProducts";
	protected static final String ATTR_NB_REFUND_PRODUCT_LIST = "nbRefundProducts";
	protected static final String ATTR_UPCOMING_PRODUCT_LIST = "tocallProducts";
	protected static final String ATTR_NB_UPCOMING_PRODUCT_LIST = "nbTocallProducts";
	protected static final String ATTR_NB_PRODUCTS = "nbProducts";
	protected static final String ATTR_PRODUCT_NAME_LIST = "productNames";
	protected static final String ATTR_BASE_LIST = "classicBases";
	protected static final String ATTR_NEW_BASE_LIST = "newBases";
	protected static final String ATTR_LAST_MONTH = "month";
	protected static final String ATTR_CURRENT_YEAR = "year";
	protected static final String ATTR_ONE_YEAR_BEFORE = "oneYearPast";
	protected static final String ATTR_BEST_SELLER = "bestSeller";
	public static final String ATTR_USERNAME_INFO = "authUserName";
	public static final String ATTR_ACCOUNT = "account";
	protected static final String ATTR_NEWS_OF_MONTH = "newsOfMonth";
	protected static final String ATTR_HIGHLIGHT_ARTICLES = "highlightArticles";

	protected static final String PARAMETER_USER_ID = "id";

	protected static final String REQUEST_MAPPING_USER_PRODUCTS = "/user/products/";

	@Autowired
	protected EavAccountService accountService;

	@Autowired
	protected PrdProductService productService;

	@Autowired
	protected PrdObservationDateService obsDateService;

	@Autowired
	protected PrdSousJacentService ssjctService;

	@Autowired
	protected EavParamsService paramsService;

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
			authentifiedUsername = EavControllerUtils.getIdentifiedName(account);
		}
		modelAndView.addObject(ATTR_USERNAME_INFO, authentifiedUsername);

		// Retrieve the list of products
		List<PrdProduct> allProducts = productService.findAll();

		// Create a list of packed product names
		List<String> packedNames = productService.getPackedNameList(allProducts);
		modelAndView.addObject(ATTR_PRODUCT_NAME_LIST, packedNames);

		// Display the number of existing products
		int nbProducts = allProducts.size();
		modelAndView.addObject(ATTR_NB_PRODUCTS, nbProducts);

		// Display the list of refund products
		int nbDays = EavUtils.NB_DAYS_REFUND_PRODUCT_LIST;
		Date now = new Date();
		List<PrdProduct> refundProducts = productService.listRefundProducts(DateUtils.addDays(now, -nbDays));
		modelAndView.addObject(ATTR_REFUND_PRODUCT_LIST, refundProducts);
		modelAndView.addObject(ATTR_NB_REFUND_PRODUCT_LIST, refundProducts != null ? refundProducts.size() : 0);
		// Display the list of upcoming products
		List<PrdProduct> upcomingProducts = productService.listUpcomingProducts(now, DateUtils.addDays(now, nbDays));
		modelAndView.addObject(ATTR_UPCOMING_PRODUCT_LIST, upcomingProducts);
		modelAndView.addObject(ATTR_NB_UPCOMING_PRODUCT_LIST, upcomingProducts != null ? upcomingProducts.size() : 0);

		// Display the current date
		PerfReviewDates prd = new PerfReviewDates();
		modelAndView.addObject(ATTR_LAST_MONTH,
				prd.getLastDayOfLastMonthAsString(new SimpleDateFormat(EavUtils.PRD_MONTH_FORMAT_PATTERN)));
		modelAndView.addObject(ATTR_CURRENT_YEAR,
				prd.getLastDayOfLastMonthAsString(new SimpleDateFormat(EavUtils.PRD_YEAR_FORMAT_PATTERN)));
		modelAndView.addObject(ATTR_ONE_YEAR_BEFORE,
				prd.getLastDayOfLastMonthAsString(new SimpleDateFormat(EavUtils.PRD_DAY_MONTH_YEAR_FORMAT_PATTERN)));

		// Retrieve the list of classic bases
		modelAndView.addObject(ATTR_BASE_LIST, ssjctService.getClassicBases());

		// Retrieve the list of new bases
		modelAndView.addObject(ATTR_NEW_BASE_LIST, ssjctService.getNewBases());

		// Set the best-seller product
		try {
			modelAndView.addObject(ATTR_BEST_SELLER, productService.findBestSeller());
		} catch (EavTechnicalException e1) {
			LOGGER.warn("Best-seller not found! Multiple best-sellers may have been set.");
		}

		// Set the news of the month
		try {
			modelAndView.addObject(ATTR_NEWS_OF_MONTH, paramsService.getEavParams());
		} catch (EavTechnicalException e) {
			LOGGER.error("FATAL: EavParams table may have not been initialized or contains duplicate entry!");
		}

		// Set the highlighted articles
		try {
			modelAndView.addObject(ATTR_HIGHLIGHT_ARTICLES, paramsService.getEavParams());
		} catch (EavTechnicalException e) {
			LOGGER.error("FATAL: EavParams table may have not been initialized or contains duplicate entry!");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/wallet")
	public String wallet(RedirectAttributes attributes) {
		String view = null;

		try {
			// Find the authenticated user and the corresponding PrdUser entity
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User u = (User) auth.getPrincipal();
			EavAccount account = accountService.findByEmail(u.getUsername());

			attributes.addAttribute(ATTR_ACCOUNT, account);

			view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_USER_PRODUCTS);

		} catch (Exception e) {
			// TODO
			e.printStackTrace();
			view = "error";

		}

		return view;
	}

}
