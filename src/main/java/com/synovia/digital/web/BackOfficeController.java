/**
 * 
 */
package com.synovia.digital.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.exceptions.EavConstraintViolationEntry;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdSousJacentService;

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

	public static final String VIEW_CREATE_PRODUCT = "create-product";
	public static final String VIEW_CREATE_SSJACENT = "create-ssjacent";
	public static final String VIEW_BACK_OFFICE = "back-office";
	public static final String VIEW_ADD_PRODUCT_DATE = "product-date";

	protected static final String REQUEST_MAPPING_SOUS_JACENT_VIEW = "/admin/sousjacents";
	protected static final String REQUEST_MAPPING_PRODUCT_VIEW = "/admin/products/{id}";
	protected static final String REQUEST_MAPPING_CREATE_SSJACENT_VIEW = "/admin/createSsjacent";
	protected static final String REQUEST_MAPPING_CREATE_PRODUCT_VIEW = "/admin/createProduct";
	protected static final String REQUEST_MAPPING_ADD_PRODUCT_DATES = "/admin/products/{id}/addDate";

	protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
	protected static final String ATTR_MESSAGE_FEEDBACK = "responseMessage";
	protected static final String PARAMETER_SOUS_JACENT_ID = "id";
	protected static final String PARAMETER_PRODUCT_ID = "id";

	protected static final String ATTR_PRODUCT_DTO = "product";
	protected static final String ATTR_PRODUCT_LIST = "products";
	protected static final String ATTR_SOUS_JACENT_DTO = "ssjacent";
	protected static final String ATTR_SOUS_JACENT_LIST = "ssjacents";

	@Autowired
	protected PrdSousJacentService sousJacentService;

	@Autowired
	protected PrdProductService productService;

	@RequestMapping()
	public String showBackOffice() {
		return VIEW_BACK_OFFICE;
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
				view = createRedirectViewPath(REQUEST_MAPPING_ADD_PRODUCT_DATES);

			} catch (EavDuplicateEntryException e) {
				LOGGER.debug("The input entry to create already exists.");
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Product entry [").append(product.getIsin())
						.append("] already exists!").toString());
				// Display the create product view.
			} catch (Exception e) {
				// TODO Display error page
				return "error";
			}
		}

		return view;
	}

	@GetMapping(value = "/products/{id}/addDate")
	public String showAddProductDate(@PathVariable("id") Long id, Model model) {
		String view = VIEW_ADD_PRODUCT_DATE;
		PrdProduct product;
		try {
			product = productService.findById(id);
			model.addAttribute("product", product);

		} catch (EavEntryNotFoundException e) {
			LOGGER.error("Product not found");
			view = "error";
		}
		return view;
	}

	@GetMapping(value = "/createSsjacent")
	public String showCreateSousJacent(Model model) {
		System.out.println("BackOfficeController.showCreateSousJacent()");
		model.addAttribute(ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto());
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

				resultView = createRedirectViewPath(REQUEST_MAPPING_CREATE_SSJACENT_VIEW);

			} catch (EavDuplicateEntryException e) {
				LOGGER.debug("The input entry to create already exists.");
				model.addAttribute(ATTR_MESSAGE_FEEDBACK, new StringBuilder("Underlying asset entry [")
						.append(ssJacent.getLabel()).append("] already exists!").toString());

			} catch (Exception e) {
				// TODO Display error page
				resultView = "error";
			}
		}

		return resultView;
	}

	private String createRedirectViewPath(String requestMapping) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		return redirectViewPath.toString();
	}
}
