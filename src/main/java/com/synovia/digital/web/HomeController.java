/**
 * 
 */
package com.synovia.digital.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.synovia.digital.service.PrdSousJacentService;
import com.synovia.digital.utils.EavConstants;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 29 déc. 2016
 */
@RestController
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	public static final String VIEW_INDEX = "index";
	public static final String VIEW_LOGIN = "login";
	public static final String VIEW_HOME = "home";

	protected static final String ATTR_REFUND_PRODUCT_LIST = "refundProducts";
	protected static final String ATTR_UPCOMING_PRODUCT_LIST = "tocallProducts";
	protected static final String ATTR_NB_PRODUCTS = "nbProducts";
	protected static final String ATTR_PRODUCT_NAME_LIST = "productNames";
	protected static final String ATTR_BASE_LIST = "classicBases";
	protected static final String ATTR_NEW_BASE_LIST = "newBases";
	protected static final String ATTR_CURRENT_MONTH = "month";
	protected static final String ATTR_CURRENT_YEAR = "year";
	protected static final String ATTR_ONE_YEAR_BEFORE = "oneYearPast";
	protected static final String ATTR_IMG_BEST_SELLER = "imgBestSeller";

	private static final int NB_DAYS_REFUND_PRODUCT_LIST = 30;

	@Autowired
	protected EavAccountService accountService;

	@Autowired
	protected PrdProductService productService;

	@Autowired
	protected PrdObservationDateService obsDateService;

	@Autowired
	protected PrdSousJacentService ssjctService;

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

		// Display the current date
		Calendar calendar = Calendar.getInstance();
		int idxCurrentMonth = calendar.get(Calendar.MONTH);
		int currentYear = calendar.get(Calendar.YEAR);
		Integer displayedYear = currentYear - EavConstants.C_MILLENARY;
		calendar.set(currentYear - 1, idxCurrentMonth - 1, 1);
		int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
		String oneYearBefore = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
		modelAndView.addObject(ATTR_CURRENT_MONTH,
				Integer.valueOf(idxCurrentMonth == 0 ? EavConstants.C_DECEMBER_INDEX : idxCurrentMonth));
		modelAndView.addObject(ATTR_CURRENT_YEAR, displayedYear);
		modelAndView.addObject(ATTR_ONE_YEAR_BEFORE, oneYearBefore);

		// Retrieve the list of classic bases
		modelAndView.addObject(ATTR_BASE_LIST, ssjctService.getClassicBases());

		// Retrieve the list of new bases
		modelAndView.addObject(ATTR_NEW_BASE_LIST, ssjctService.getNewBases());

		// Set the best-seller product
		String imagePath = "img/default-best-seller.jpg";
		File image = productService.getBestSellerImage();
		if (image != null) {
			imagePath = image.getPath();
		}
		modelAndView.addObject(ATTR_IMG_BEST_SELLER, imagePath);

		//		try {
		//			PrdProduct bestSeller = productService.findBestSeller();
		//			if (bestSeller == null) {
		//				LOGGER.info("No best-seller is set. A default image is displayed as the best-seller product.");
		//
		//			} else {
		//				// Get the image of the best-seller
		//				File image = productService.getImage(bestSeller);
		//				if (image!=null)
		//					defaultImagePath = image.getPath();
		//
		//			}
		//		} catch (EavTechnicalException e) {
		//			LOGGER.info("Multiple best-seller are set. A default image is displayed instead.");
		//		}
		//		modelAndView.addObject(ATTR_IMG_BEST_SELLER, defaultImagePath);
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
