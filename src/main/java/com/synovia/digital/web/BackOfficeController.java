/**
 * 
 */
package com.synovia.digital.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synovia.digital.dto.EavParamsDto;
import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.PrdProductListDto;
import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.exceptions.EavConstraintViolationEntry;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.service.EavAccountService;
import com.synovia.digital.service.EavParamsService;
import com.synovia.digital.service.PrdCouponDateService;
import com.synovia.digital.service.PrdEarlierRepaymentDateService;
import com.synovia.digital.service.PrdObservationDateService;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdSousJacentService;
import com.synovia.digital.service.PrdUserService;
import com.synovia.digital.utils.EavControllerUtils;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 31 janv. 2017
 */
@Controller
@RequestMapping(value = "/admin")
public class BackOfficeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BackOfficeController.class);

	public static final String VIEW_CREATE_PRODUCT = "bo-create-product";
	public static final String VIEW_CREATE_SSJACENT = "bo-create-ssjacent";
	public static final String VIEW_BACK_OFFICE = "back-office";
	public static final String VIEW_ADD_PRODUCT_DATE = "bo-product-date";
	public static final String VIEW_ACCOUNTS = "bo-accounts";
	public static final String VIEW_TESTS_MENU = "bo-product-tests";
	public static final String VIEW_ERROR = "error";
	public static final String VIEW_USER_INFO = "bo-user-info";
	public static final String VIEW_UPDATE_PRODUCT = "bo-update-product";

	protected static final String REQUEST_MAPPING_SOUS_JACENT_VIEW = "/admin/sousjacents";
	protected static final String REQUEST_MAPPING_PRODUCT_VIEW = "/admin/products/{id}";
	protected static final String REQUEST_MAPPING_CREATE_SSJACENT_VIEW = "/admin/createSsjacent";
	protected static final String REQUEST_MAPPING_CREATE_PRODUCT_VIEW = "/admin/createProduct";
	protected static final String REQUEST_MAPPING_TEST_CREATE_PRODUCT_VIEW = "/admin/tests";
	protected static final String REQUEST_MAPPING_ADD_PRODUCT_DATES = "/admin/products/{id}/addDate";
	protected static final String REQUEST_MAPPING_UPDATE_PRODUCT = "/admin/products/{id}/addDate";

	protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
	protected static final String ATTR_MESSAGE_FEEDBACK = "responseMessage";
	protected static final String PARAMETER_SOUS_JACENT_ID = "id";
	protected static final String PARAMETER_PRODUCT_ID = "id";

	protected static final String ATTR_PRODUCT_DTO = "product";
	protected static final String ATTR_PRODUCT_TO_UPDATE_DTO = "toUpdate";
	protected static final String ATTR_PRODUCT = "product";
	protected static final String ATTR_PRODUCT_LIST = "products";
	protected static final String ATTR_SOUS_JACENT_DTO = "ssjacent";
	protected static final String ATTR_SOUS_JACENT_LIST = "ssjacents";
	protected static final String ATTR_OBS_DATE_DTO = "obsDate";
	protected static final String ATTR_OBS_DATE_LIST = "obsDates";
	protected static final String ATTR_ER_DATE_DTO = "earlyPayDate";
	protected static final String ATTR_ER_DATE_LIST = "earlyPayDates";
	protected static final String ATTR_COUPON_DATE_DTO = "couponDate";
	protected static final String ATTR_COUPON_DATE_LIST = "couponDates";
	protected static final String ATTR_ACCOUNT_LIST = "accounts";
	protected static final String ATTR_PRODUCT_IMAGE_FILENAME = "imageFile";
	protected static final String ATTR_PRODUCT_TS_FILENAME = "termSheetFile";
	protected static final String ATTR_PRODUCT_MKT_FILENAME = "marketingFile";
	protected static final String ATTR_PRODUCT_FEASE_FILENAME = "feaseFile";
	protected static final String ATTR_NEWS_MONTH_DTO = "newsOfMonth";
	protected static final String ATTR_HOME_ARTICLES_DTO = "highlightArticles";
	protected static final String ATTR_ACCOUNT = "account";
	protected static final String ATTR_USER = "prdUser";

	@Autowired
	protected PrdSousJacentService sousJacentService;

	@Autowired
	protected PrdProductService productService;

	@Autowired
	protected PrdObservationDateService obsDateService;

	@Autowired
	protected PrdEarlierRepaymentDateService earlyPayDateService;

	@Autowired
	protected PrdCouponDateService couponDateService;

	@Autowired
	protected EavAccountService accountService;

	@Autowired
	protected EavParamsService paramsService;

	@Autowired
	protected PrdUserService userService;

	@RequestMapping()
	public ModelAndView showBackOffice(@ModelAttribute PrdProductDto bestSellerDto,
			@Valid @ModelAttribute EavParamsDto newsMonthDto, @Valid @ModelAttribute EavParamsDto homeArticlesDto,
			BindingResult result, ModelAndView modelAndView) {
		modelAndView.setViewName(VIEW_BACK_OFFICE);
		modelAndView.addObject(ATTR_PRODUCT_DTO, new PrdProductDto());
		modelAndView.addObject(ATTR_NEWS_MONTH_DTO, new EavParamsDto());
		modelAndView.addObject(ATTR_HOME_ARTICLES_DTO, new EavParamsDto());

		// Find the corresponding product entity
		if (bestSellerDto.getIsin() != null) {
			try {
				PrdProduct p = productService.setBestSeller(bestSellerDto);
				modelAndView.addObject(ATTR_MESSAGE_FEEDBACK,
						new StringBuilder("The product ").append(p.getLabel()).append(" is now a BEST-SELLER!!"));

			} catch (EavEntryNotFoundException e) {
				modelAndView.addObject(ATTR_MESSAGE_FEEDBACK,
						"Product not found!! Please enter an existing ISIN code.");
			}
		}

		if (result.hasErrors()) {
			LOGGER.warn("EavParams cannot be updated because of errors {}.", result.getAllErrors());
			modelAndView.addObject(ATTR_MESSAGE_FEEDBACK, "Invalid parameters.");
			return modelAndView;
		}

		// Set the news of the month
		if (newsMonthDto.getNumberOfTheMonth() != null) {
			String feedback = null;
			try {
				paramsService.updateParams(newsMonthDto);
				feedback = "The key news of the month have been updated.";
			} catch (EavEntryNotFoundException e) {
				feedback = "No Eavest params found.";
			}
			modelAndView.addObject(ATTR_MESSAGE_FEEDBACK, feedback);
		}

		// Set highlights
		if (homeArticlesDto.getRightArticleUrl() != null || homeArticlesDto.getLeftArticleUrl() != null) {
			String feedback = null;
			try {
				paramsService.updateParams(homeArticlesDto);
				feedback = "Home highlighted articles have been updated.";
			} catch (EavEntryNotFoundException e) {
				feedback = "No Eavest params found.";
			}
			modelAndView.addObject(ATTR_MESSAGE_FEEDBACK, feedback);

		}
		return modelAndView;
	}

	@GetMapping(value = "/accounts")
	public String showEavAccounts(Model model) {
		model.addAttribute(ATTR_ACCOUNT_LIST, accountService.findAll());
		return VIEW_ACCOUNTS;
	}

	@GetMapping(value = "/tests")
	public String showTestMenu(Model model) {
		LOGGER.debug("Show menu tests");
		model.addAttribute(ATTR_PRODUCT_DTO, new PrdProductDto());
		model.addAttribute(ATTR_SOUS_JACENT_LIST, sousJacentService.findAll());
		model.addAttribute(ATTR_PRODUCT_LIST, productService.findAll());
		return VIEW_TESTS_MENU;
	}

	@GetMapping(value = "/createProduct")
	public String showCreateProduct(Model model) {
		LOGGER.debug("Show view create product");
		model.addAttribute(ATTR_PRODUCT_DTO, new PrdProductDto());
		List<PrdSousJacent> sousJacentList = sousJacentService.findAll();
		model.addAttribute(ATTR_SOUS_JACENT_LIST, sousJacentList);
		model.addAttribute(ATTR_PRODUCT_LIST, productService.findAll());
		return VIEW_CREATE_PRODUCT;
	}

	@PostMapping(value = "/createProduct")
	public String addProduct(@Valid @ModelAttribute PrdProductDto product, BindingResult result,
			RedirectAttributes attributes, Model model) {
		System.out.println("BackOfficeController.addProduct()");
		model.addAttribute(ATTR_PRODUCT_DTO, new PrdProductDto());
		List<PrdSousJacent> sousJacentList = sousJacentService.findAll();
		model.addAttribute(ATTR_SOUS_JACENT_LIST, sousJacentList);
		model.addAttribute(ATTR_PRODUCT_LIST, productService.findAll());
		String view = VIEW_CREATE_PRODUCT;
		if (result.hasErrors()) {
			// Deal with entry errors
			LOGGER.error("The input entry violates constraints.");
			for (ObjectError oe : result.getAllErrors()) {
				LOGGER.debug(oe.toString());
			}
			model.addAttribute(ATTR_MESSAGE_FEEDBACK, "Product entry is incomplete!");

		} else {
			try {
				PrdProduct added = productService.add(product);
				LOGGER.debug("The input entry was added.");

				// TODO i18n
				attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Product entry [")
						.append(added.getIsin()).append("] has been successfully created!").toString());
				attributes.addFlashAttribute(ATTR_PRODUCT_LIST, productService.findAll());
				attributes.addAttribute(PARAMETER_PRODUCT_ID, added.getId());
				// Redirect the view
				view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_ADD_PRODUCT_DATES);

			} catch (EavDuplicateEntryException e) {
				LOGGER.debug("The input entry to create already exists.");
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Product entry [").append(product.getIsin())
						.append("] already exists!").toString());
				// Display the create product view.
			} catch (Exception e) {
				// TODO Display error page
				return VIEW_ERROR;
			}
		}

		return view;
	}

	@PostMapping(value = "/products/{id}/updateImage")
	public String updateProductImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id,
			RedirectAttributes attributes) {
		String view = VIEW_ADD_PRODUCT_DATE;

		// Store the product data in the fdwh
		try {
			PrdProduct product = productService.findById(id);
			productService.storeImage(product, file);

			attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
					new StringBuilder("Product image has been stored!").toString());
			attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute("product", product);

			File imageFile = productService.getImage(product);
			if (imageFile != null) {
				attributes.addFlashAttribute(ATTR_PRODUCT_IMAGE_FILENAME, imageFile.getName());
			}
			// Redirect the view
			view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_UPDATE_PRODUCT);

		} catch (EavTechnicalException e) {
			view = VIEW_ERROR;
		}

		return view;
	}

	@PostMapping(value = "/products/{id}/updateTermSheet")
	public String updateProductTermSheet(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id,
			RedirectAttributes attributes) {
		String view = VIEW_ADD_PRODUCT_DATE;

		// Store the product data in the fdwh
		try {
			PrdProduct product = productService.findById(id);
			productService.storeTermSheet(product, file);

			attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
					new StringBuilder("Product term sheet has been stored!").toString());
			attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute("product", product);

			// Redirect the view
			view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_UPDATE_PRODUCT);

		} catch (EavTechnicalException e) {
			view = VIEW_ERROR;
		}

		return view;
	}

	@PostMapping(value = "/products/{id}/updateMarketingDoc")
	public String updateProductMarketingDoc(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id,
			RedirectAttributes attributes) {
		String view = VIEW_ADD_PRODUCT_DATE;

		// Store the product data in the fdwh
		try {
			PrdProduct product = productService.findById(id);
			productService.storeMarketingDoc(product, file);

			attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
					new StringBuilder("Product marketing document has been stored!").toString());
			attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute("product", product);

			// Redirect the view
			view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_UPDATE_PRODUCT);

		} catch (EavTechnicalException e) {
			view = VIEW_ERROR;
		}

		return view;
	}

	@PostMapping(value = "/products/{id}/updateFease")
	public String updateProductFease(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id,
			RedirectAttributes attributes) {
		String view = VIEW_ADD_PRODUCT_DATE;

		// Store the product data in the fdwh
		try {
			PrdProduct product = productService.findById(id);
			productService.storeFease(product, file);

			attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
					new StringBuilder("Product fease document has been stored!").toString());
			attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute("product", product);

			// Redirect the view
			view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_UPDATE_PRODUCT);

		} catch (EavTechnicalException e) {
			view = VIEW_ERROR;
		}

		return view;
	}

	@GetMapping(value = "/products/{id}/update")
	public String showUpdateProduct(@PathVariable("id") Long id, Model model) {
		String view = VIEW_UPDATE_PRODUCT;
		try {
			model.addAttribute(ATTR_OBS_DATE_DTO, new PrdObservationDate());
			model.addAttribute(ATTR_ER_DATE_DTO, new PrdEarlierRepaymentDate());
			model.addAttribute(ATTR_COUPON_DATE_DTO, new PrdCouponDate());
			model.addAttribute("prdFiles", new ArrayList<String>());

			// Display existing attributes
			PrdProduct product = productService.findById(id);
			model.addAttribute(ATTR_PRODUCT, product);
			model.addAttribute(ATTR_PRODUCT_TO_UPDATE_DTO, new PrdProductDto(product));
			model.addAttribute(ATTR_SOUS_JACENT_LIST, sousJacentService.findAll());
			model.addAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
			model.addAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
			model.addAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));

		} catch (EavEntryNotFoundException e) {
			LOGGER.error("Product not found");
			view = VIEW_ERROR;
		}
		return view;
	}

	@PostMapping(value = "/products/{id}/update")
	public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("toUpdate") PrdProductDto toUpdate,
			RedirectAttributes attributes) {
		String view = VIEW_UPDATE_PRODUCT;
		try {
			PrdProduct product = productService.findById(id);
			productService.update(product, toUpdate);

			attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
					new StringBuilder("Product has been updated!").toString());
			attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
			attributes.addFlashAttribute(ATTR_PRODUCT, product);
			attributes.addFlashAttribute(ATTR_PRODUCT_TO_UPDATE_DTO, toUpdate);
			attributes.addFlashAttribute(ATTR_SOUS_JACENT_LIST, sousJacentService.findAll());

			attributes.addAttribute(PARAMETER_PRODUCT_ID, id);

			// Redirect the view
			view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_UPDATE_PRODUCT);

		} catch (EavTechnicalException e) {
			view = VIEW_ERROR;
		}
		return view;
	}

	@PostMapping(value = "/products/{id}/delete")
	public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
		attributes.addFlashAttribute(ATTR_PRODUCT_DTO, new PrdProductDto());
		attributes.addFlashAttribute(ATTR_SOUS_JACENT_LIST, sousJacentService.findAll());
		attributes.addFlashAttribute(ATTR_PRODUCT_LIST, productService.findAll());
		String view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_TEST_CREATE_PRODUCT_VIEW);
		try {
			PrdProduct toDelete = productService.findById(id);
			String label = toDelete.getLabel();
			String isin = toDelete.getIsin();
			productService.delete(toDelete);
			attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Product ").append(isin).append(" (")
					.append(label).append(") has been successfully deleted.").toString());

		} catch (EavEntryNotFoundException e) {
			e.printStackTrace();
			view = VIEW_ERROR;
		}

		return view;
	}

	@GetMapping(value = "/products/{id}/addDate")
	public String showAddProductDate(@PathVariable("id") Long id, Model model) {
		String view = VIEW_ADD_PRODUCT_DATE;
		model.addAttribute(ATTR_OBS_DATE_DTO, new PrdProductDateDto());
		model.addAttribute(ATTR_ER_DATE_DTO, new PrdProductDateDto());
		model.addAttribute(ATTR_COUPON_DATE_DTO, new PrdProductDateDto());
		model.addAttribute("prdFiles", new ArrayList<String>());
		PrdProduct product;
		try {
			product = productService.findById(id);
			model.addAttribute(ATTR_PRODUCT, product);

		} catch (EavEntryNotFoundException e) {
			LOGGER.error("Product not found");
			view = VIEW_ERROR;
		}
		return view;
	}

	@PostMapping(value = "/products/{id}/addObsDate")
	public String addObservationDate(@PathVariable("id") Long id,
			@Valid @ModelAttribute(ATTR_OBS_DATE_DTO) PrdProductDateDto obsDateDto, BindingResult result,
			RedirectAttributes attributes, Model model) {
		LOGGER.info(new StringBuilder("Call 'add date' for product id ").append(id).toString());
		String view = VIEW_ADD_PRODUCT_DATE;
		try {
			PrdProduct product = productService.findById(id);

			if (result.hasErrors()) {
				model.addAttribute("product", product);
				model.addAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, "Cannot add the date. An error occurs!");

			} else {
				obsDateDto.setIdPrdProduct(id);
				obsDateService.add(obsDateDto);

				LOGGER.debug("The input date was added.");

				attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
						new StringBuilder("Input date has been added!").toString());
				attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute("product", product);
				// Redirect the view
				view = EavControllerUtils.createRedirectViewPath("/admin/products/{id}/addDate");

			}
		} catch (EavEntryNotFoundException e) {
			LOGGER.error("Product not found");
			view = VIEW_ERROR;
		}

		return view;
	}

	@PostMapping(value = "/products/{id}/addCouponDate")
	public String addCouponDate(@PathVariable("id") Long id,
			@Valid @ModelAttribute(ATTR_COUPON_DATE_DTO) PrdProductDateDto couponDateDto, BindingResult result,
			RedirectAttributes attributes, Model model) {
		String view = VIEW_ADD_PRODUCT_DATE;
		try {
			PrdProduct product = productService.findById(id);

			if (result.hasErrors()) {
				model.addAttribute("product", product);
				model.addAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, "Cannot add the date. An error occurs!");

			} else {
				couponDateDto.setIdPrdProduct(id);
				couponDateService.add(couponDateDto);
				LOGGER.debug("The input date was added.");

				attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
						new StringBuilder("Input date has been added!").toString());
				attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute("product", product);
				// Redirect the view
				view = EavControllerUtils.createRedirectViewPath("/admin/products/{id}/addDate");

			}
		} catch (EavEntryNotFoundException e) {
			LOGGER.error("Product not found");
			view = VIEW_ERROR;
		}

		return view;
	}

	@PostMapping(value = "/products/{id}/addEarlyRepaymentDate")
	public String addEarlyRepaymentDate(@PathVariable("id") Long id,
			@Valid @ModelAttribute(ATTR_ER_DATE_DTO) PrdProductDateDto earlyPayDateDto, BindingResult result,
			RedirectAttributes attributes, Model model) {
		String view = VIEW_ADD_PRODUCT_DATE;
		try {
			PrdProduct product = productService.findById(id);

			if (result.hasErrors()) {
				model.addAttribute("product", product);
				model.addAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, "Cannot add the date. An error occurs!");

			} else {
				earlyPayDateDto.setIdPrdProduct(id);
				earlyPayDateService.add(earlyPayDateDto);
				LOGGER.debug("The input date was added.");

				attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK,
						new StringBuilder("Input date has been added!").toString());
				attributes.addFlashAttribute(ATTR_OBS_DATE_LIST, obsDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute(ATTR_ER_DATE_LIST, earlyPayDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute(ATTR_COUPON_DATE_LIST, couponDateService.findByIdPrdProduct(id));
				attributes.addFlashAttribute("product", product);
				// Redirect the view
				view = EavControllerUtils.createRedirectViewPath("/admin/products/{id}/addDate");

			}
		} catch (EavEntryNotFoundException e) {
			LOGGER.error("Product not found");
			view = VIEW_ERROR;
		}

		return view;
	}

	@GetMapping(value = "/createSsjacent")
	public String showCreateSousJacent(Model model) {
		System.out.println("BackOfficeController.showCreateSousJacent()");
		model.addAttribute(ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto());
		model.addAttribute(ATTR_SOUS_JACENT_LIST, sousJacentService.findAll());
		//		ssjacentRepo.save(ssJacent);
		return VIEW_CREATE_SSJACENT;

	}

	@PostMapping(value = "/createSsjacent")
	public String createSousJacent(@Valid @ModelAttribute("ssjacvent") PrdSousjacentDto ssJacent, BindingResult result,
			RedirectAttributes attributes, Model model) throws EavConstraintViolationEntry {
		String resultView = VIEW_CREATE_SSJACENT;
		List<PrdSousJacent> sousJacentList = sousJacentService.findAll();
		model.addAttribute(ATTR_SOUS_JACENT_LIST, sousJacentList);
		model.addAttribute(ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto());
		if (result.hasErrors()) {
			LOGGER.error("The input entry violates constraints.");
			model.addAttribute(ATTR_MESSAGE_FEEDBACK, "Underlying asset entry is incomplete!");

		} else {
			try {
				PrdSousJacent added = sousJacentService.add(ssJacent);
				LOGGER.debug("The input entry was added.");

				// TODO i18n
				// Redirect the page with attributes
				attributes.addFlashAttribute(ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto());
				attributes.addFlashAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Underlying asset entry [")
						.append(added.getLabel()).append("] was successfully created!").toString());
				attributes.addFlashAttribute(ATTR_SOUS_JACENT_LIST, sousJacentService.findAll());

				resultView = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_CREATE_SSJACENT_VIEW);

			} catch (EavDuplicateEntryException e) {
				LOGGER.debug("The input entry to create already exists.");
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Underlying asset entry [")
						.append(ssJacent.getLabel()).append("] already exists!").toString());

			} catch (Exception e) {
				// TODO Display error page
				resultView = VIEW_ERROR;
			}
		}

		return resultView;
	}

	@GetMapping(value = "/users/{id}")
	public String userInfos(@PathVariable("id") Long id, Model model) {
		String resultView = VIEW_USER_INFO;
		try {
			PrdUser user = userService.findById(id);
			EavAccount userAccount = user.getAccount();

			model.addAttribute(ATTR_USER, user);
			model.addAttribute(ATTR_ACCOUNT, userAccount);
			model.addAttribute(ATTR_PRODUCT_LIST, productService.findAll());
			model.addAttribute("selectedProducts", new PrdProductListDto());

		} catch (EavEntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultView = VIEW_ERROR;
		}

		return resultView;
	}

	@PostMapping(value = "/users/{id}/addProducts")
	public String addUserProducts(@PathVariable("id") Long id) {
		return null;
	}

}
