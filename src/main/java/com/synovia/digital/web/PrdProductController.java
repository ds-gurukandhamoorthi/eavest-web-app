/**
 * 
 */
package com.synovia.digital.web;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.synovia.digital.dto.PrdProductFilterDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.service.EavAccountService;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdSousJacentService;
import com.synovia.digital.service.PrdUserService;
import com.synovia.digital.utils.EavControllerUtils;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 20 mars 2017
 */
@Controller
@RequestMapping(value = "/products")
public class PrdProductController {
	private static Logger LOGGER = LoggerFactory.getLogger(PrdProductController.class);

	public static final String VIEW_PRODUCTS = "products";
	public static final String VIEW_ONE_PRODUCT = "product";

	protected static final String ATTR_PRODUCT_LIST = "products";
	protected static final String ATTR_PRODUCT = "product";
	protected static final String ATTR_PAGE_BEGIN_SLICE = "beginPageNb";
	protected static final String ATTR_PAGE_END_SLICE = "endPageNb";
	protected static final String ATTR_PAGE_CURRENT_SLICE = "currentPageNb";
	protected static final String ATTR_PAGE_TOTAL_SLICES = "totalPageNb";
	protected static final String ATTR_SLICE_ITERATOR = "sliceIndexes";
	protected static final String ATTR_BANK_LIST = "bankNames";
	protected static final String ATTR_ISIN_LIST = "isinCodes";
	protected static final String ATTR_PRODUCT_NAME_LIST = "productNames";
	protected static final String ATTR_SSJCT_NAME_LIST = "baseNames";
	protected static final String ATTR_PRD_FILTER = "filter";
	protected static final String ATTR_PRD_FILTER_NAME = "nm";
	protected static final String ATTR_PRD_FILTER_ISIN = "is";
	protected static final String ATTR_PRD_FILTER_BASE = "sj";
	protected static final String ATTR_PRD_FILTER_EAVEST = "ev";
	protected static final String ATTR_PRD_FILTER_BANK = "bk";
	protected static final String ATTR_RESPONSE_MESSAGE = "response";
	protected static final String ATTR_DISPLAY_WARNING = "displayWarning";

	protected static final String PARAMETER_PAGE_NUMBER = "pageNumber";
	protected static final String PARAMETER_PRODUCT_ID = "id";

	protected static final String REQUEST_MAPPING_PRODUCTS_PAGE = "/products/pages/{pageNumber}/filter";

	protected static final String REQUEST_MAPPING_PRODUCT = "/products/{id}";

	protected static final String REQUEST_MAPPING_LOGIN = "/login";

	private static final int SIZE_PRODUCTS_PAGE = 12;
	/** Number of max displayed pages */
	private static final int SLICE_SIZE = 10;

	@Autowired
	protected PrdProductService productService;

	@Autowired
	protected PrdSousJacentService sousJacentService;

	@Autowired
	protected EavAccountService accountService;

	@Autowired
	protected PrdUserService userService;

	@GetMapping()
	public String listProducts(RedirectAttributes attributes) {
		// Redirect the page to the first page of products
		Integer firstPageNumber = 1;
		attributes.addAttribute(PARAMETER_PAGE_NUMBER, firstPageNumber);
		attributes.addAttribute(ATTR_PRD_FILTER_NAME, "");
		attributes.addAttribute(ATTR_PRD_FILTER_ISIN, "");
		attributes.addAttribute(ATTR_PRD_FILTER_BASE, "");
		attributes.addAttribute(ATTR_PRD_FILTER_EAVEST, "");
		attributes.addAttribute(ATTR_PRD_FILTER_BANK, "");
		return EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_PRODUCTS_PAGE);
	}

	@PostMapping(value = "/filter")
	public String filterProductsPage(@ModelAttribute("filter") PrdProductFilterDto filterDto,
			RedirectAttributes attributes) {
		// Redirect the page to the first page of products
		Integer firstPageNumber = 1;
		attributes.addAttribute(PARAMETER_PAGE_NUMBER, firstPageNumber);
		attributes.addAttribute(ATTR_PRD_FILTER_NAME, filterDto.getLabel());
		attributes.addAttribute(ATTR_PRD_FILTER_ISIN, filterDto.getIsin());
		attributes.addAttribute(ATTR_PRD_FILTER_BASE, filterDto.getLabelSousJacent());
		attributes.addAttribute(ATTR_PRD_FILTER_EAVEST,
				filterDto.getIsEavest() == null ? "" : filterDto.getIsEavest() == null);
		attributes.addAttribute(ATTR_PRD_FILTER_BANK, filterDto.getDeliver());

		return EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_PRODUCTS_PAGE);
	}

	@GetMapping(value = "/pages/{pageNumber}/filter")
	public String showFilterProductsPage(@PathVariable Integer pageNumber, @RequestParam("nm") String label,
			@RequestParam("is") String isin, @RequestParam("sj") String base, @RequestParam("ev") Boolean isEavest,
			@RequestParam("bk") String deliver, Model model, HttpSession session, Principal principal) {
		// Display warning message
		Long now = new Date().getTime();
		Long lastAccess = session.getLastAccessedTime();
		boolean display = session.getAttribute(ATTR_DISPLAY_WARNING) == null
				|| now - lastAccess >= EavControllerUtils.DISPLAY_MODAL_TIMEOUT_MS;
		session.setAttribute(ATTR_DISPLAY_WARNING, display);

		// Display user info
		if (session.getAttribute(HomeController.ATTR_USERNAME_INFO) == null) {
			// Retrieve user info
			if (principal != null) {
				String email = principal.getName();
				EavAccount account = accountService.findByEmail(email);
				session.setAttribute(HomeController.ATTR_USERNAME_INFO, EavControllerUtils.getIdentifiedName(account));
			}
		}

		// Retrieve the list of existing values for the filters
		Set<String> prdNames = new HashSet<>();
		Set<String> prdDelivers = new HashSet<>();
		Set<String> prdIsinCodes = new HashSet<>();
		for (PrdProduct p : productService.findAll()) {
			prdNames.add(p.getLabel());
			prdDelivers.add(p.getDeliver());
			prdIsinCodes.add(p.getIsin());
		}
		model.addAttribute(ATTR_PRODUCT_NAME_LIST, prdNames);
		model.addAttribute(ATTR_BANK_LIST, prdDelivers);
		model.addAttribute(ATTR_ISIN_LIST, prdIsinCodes);

		Set<String> ssjctLabels = new HashSet<>();
		for (PrdSousJacent s : sousJacentService.findAll()) {
			ssjctLabels.add(s.getLabel());
		}
		model.addAttribute(ATTR_SSJCT_NAME_LIST, ssjctLabels);

		// Filter and page
		Page<PrdProduct> page = null;
		PrdProductFilterDto filterDto = new PrdProductFilterDto(isin, label, deliver, base, isEavest);

		PrdSousJacent psj;
		LOGGER.info("products: A filter has been set {}", filterDto);
		Pageable pageRequest = new PageRequest(pageNumber - 1, SIZE_PRODUCTS_PAGE, Sort.Direction.DESC, "launchDate");
		try {
			psj = StringUtils.isNotBlank(filterDto.getLabelSousJacent())
					? sousJacentService.findByLabel(filterDto.getLabelSousJacent()) : null;

			page = productService.filterLikeAndPage(filterDto.getIsin(), filterDto.getLabel(), filterDto.getDeliver(),
					psj, filterDto.getIsEavest(), pageRequest);

		} catch (EavEntryNotFoundException e) {
			e.printStackTrace();
			page = new PageImpl<PrdProduct>(new ArrayList<PrdProduct>());

		}

		int total = page.getTotalPages();
		int current = page.getNumber() + 1;
		int begin = (int) Math.max(1, current - Math.floor(SLICE_SIZE / 2));
		int end = Math.min(begin + SLICE_SIZE, total);

		Integer[] iterator = new Integer[end - begin + 1];
		for (int i = 0; i < iterator.length; i++) {
			iterator[i] = i;
		}

		model.addAttribute(ATTR_PRD_FILTER, filterDto);
		model.addAttribute(ATTR_SLICE_ITERATOR, iterator);
		model.addAttribute(ATTR_PRODUCT_LIST, page);
		model.addAttribute(ATTR_PAGE_BEGIN_SLICE, begin);
		model.addAttribute(ATTR_PAGE_END_SLICE, end);
		model.addAttribute(ATTR_PAGE_CURRENT_SLICE, current);
		model.addAttribute(ATTR_PAGE_TOTAL_SLICES, total);
		return VIEW_PRODUCTS;
	}

	@GetMapping(value = "/{id}")
	public String showProduct(@PathVariable Long id, Model model, HttpSession session, Principal principal) {
		String view = null;
		// Display user info
		if (session.getAttribute(HomeController.ATTR_USERNAME_INFO) == null) {
			if (principal != null) {
				// Retrieve user info
				String email = principal.getName();
				EavAccount account = accountService.findByEmail(email);
				session.setAttribute(HomeController.ATTR_USERNAME_INFO, EavControllerUtils.getIdentifiedName(account));
			}
		}

		try {
			model.addAttribute(ATTR_PRODUCT, productService.findById(id));
			view = VIEW_ONE_PRODUCT;

		} catch (EavEntryNotFoundException e) {
			model.addAttribute(EavControllerUtils.ATTR_ERROR_RESPONSE, EavControllerUtils.I18N_ERROR_CODE);
			view = EavControllerUtils.VIEW_ERROR;
		}
		return view;
	}

	@GetMapping(value = "/{id}/image")
	@ResponseBody
	public byte[] getProductImage(@PathVariable Long id) {
		byte[] result = null;
		File prdImage;
		try {
			prdImage = productService.getImage(productService.findById(id));
			result = org.apache.commons.io.FileUtils.readFileToByteArray(prdImage);
			LOGGER.debug("Image {} has been successfully read", prdImage);

		} catch (EavEntryNotFoundException e) {
			LOGGER.warn("Product not found {}", id);

		} catch (IOException e) {
			LOGGER.warn("IOException while reading product image");
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping(value = "/{id}/fease")
	@ResponseBody
	public ResponseEntity<byte[]> getProductFease(@PathVariable Long id) {
		ResponseEntity<byte[]> result = null;
		File prdFease;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			prdFease = productService.getFease(productService.findById(id));
			LOGGER.debug("Reading fease {}", prdFease);
			String filename = prdFease.getName();
			headers.setContentDispositionFormData(filename, filename);
			//			result = org.apache.commons.io.FileUtils.readFileToByteArray(prdFease);
			result = new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(prdFease), headers,
					HttpStatus.OK);

		} catch (EavEntryNotFoundException e) {
			LOGGER.warn("Product not found {}", id);

		} catch (IOException e) {
			LOGGER.warn("IOException while reading product fease");
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping(value = "/{id}/market")
	@ResponseBody
	public ResponseEntity<byte[]> getProductMarketingDoc(@PathVariable Long id) {
		ResponseEntity<byte[]> result = null;
		File prdMarket;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			prdMarket = productService.getMarketingDoc(productService.findById(id));
			LOGGER.debug("Reading marketing document {}", prdMarket);
			String filename = prdMarket.getName();
			headers.setContentDispositionFormData(filename, filename);
			result = new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(prdMarket), headers,
					HttpStatus.OK);

		} catch (EavEntryNotFoundException e) {
			LOGGER.warn("Product not found {}", id);

		} catch (IOException e) {
			LOGGER.warn("IOException while reading product marketin document");
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping(value = "/{id}/tsheet")
	@ResponseBody
	public ResponseEntity<byte[]> getProductTermSheet(@PathVariable Long id) {
		ResponseEntity<byte[]> result = null;
		File prdTS;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			prdTS = productService.getTermSheet(productService.findById(id));
			LOGGER.debug("Reading term sheet {}", prdTS);
			String filename = prdTS.getName();
			headers.setContentDispositionFormData(filename, filename);
			result = new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(prdTS), headers,
					HttpStatus.OK);

		} catch (EavEntryNotFoundException e) {
			LOGGER.warn("Product not found {}", id);

		} catch (IOException e) {
			LOGGER.warn("IOException while reading product term sheet");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/{id}/add")
	public String addProduct(@PathVariable Long id, RedirectAttributes attributes, Principal principal) {
		String view = null;
		try {
			if (principal != null) {
				EavAccount account = accountService.findByEmail(principal.getName());
				PrdUser user = account.getPrdUser();

				PrdProduct product = productService.findById(id);
				// Get the product list
				userService.addProduct(user, product);

				attributes.addFlashAttribute(ATTR_RESPONSE_MESSAGE,
						new StringBuilder("Product ").append(product.getIsin()).append(" added to the portofolio of ")
								.append(account.getFirstName().toString()));

				attributes.addAttribute(PARAMETER_PRODUCT_ID, id);

				view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_PRODUCT);

			} else {
				view = EavControllerUtils.createRedirectViewPath(REQUEST_MAPPING_LOGIN);
			}

		} catch (EavEntryNotFoundException e) {
			e.printStackTrace();
			attributes.addFlashAttribute(EavControllerUtils.ATTR_ERROR_RESPONSE, EavControllerUtils.I18N_ERROR_CODE);
			view = EavControllerUtils.createRedirectViewPath(EavControllerUtils.REQUEST_MAPPING_ERROR);
		}

		return view;
	}

}
