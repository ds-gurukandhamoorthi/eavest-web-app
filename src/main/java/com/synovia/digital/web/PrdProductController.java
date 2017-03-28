/**
 * 
 */
package com.synovia.digital.web;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.service.PrdProductService;

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

	protected static final String ATTR_PRODUCT_LIST = "products";

	@Autowired
	protected PrdProductService productService;

	@GetMapping()
	public String listProducts(Model model) {
		System.out.println("PrdProductController.listProducts()");
		model.addAttribute(ATTR_PRODUCT_LIST, productService.findAll());
		return VIEW_PRODUCTS;
	}

	@GetMapping(value = "/{id}/image")
	@ResponseBody
	public byte[] getProductImage(@PathVariable Long id) {
		LOGGER.info("Call getProductImage");
		byte[] result = null;
		File prdImage;
		try {
			prdImage = productService.getImage(productService.findById(id));
			LOGGER.info("Reading image {}", prdImage);
			result = org.apache.commons.io.FileUtils.readFileToByteArray(prdImage);
			LOGGER.info("Image {} has been successfully read", prdImage);

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
		LOGGER.info("Call getProductFease");
		ResponseEntity<byte[]> result = null;
		// TODO
		File prdFease;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			prdFease = productService.getFease(productService.findById(id));
			LOGGER.info("Reading fease {}", prdFease);
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
		LOGGER.info("Call getProductMarketingDoc");
		ResponseEntity<byte[]> result = null;
		// TODO
		File prdMarket;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			prdMarket = productService.getMarketingDoc(productService.findById(id));
			LOGGER.info("Reading marketing document {}", prdMarket);
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
		LOGGER.info("Call getProductTermSheet");
		ResponseEntity<byte[]> result = null;
		// TODO
		File prdTS;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			prdTS = productService.getTermSheet(productService.findById(id));
			LOGGER.info("Reading term sheet {}", prdTS);
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
}
