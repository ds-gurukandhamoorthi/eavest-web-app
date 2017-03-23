/**
 * 
 */
package com.synovia.digital.web;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	protected PrdProductService productService;

	@GetMapping()
	public String listProducts(Model model) {
		System.out.println("PrdProductController.listProducts()");
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
			byte[] image = org.apache.commons.io.FileUtils.readFileToByteArray(prdImage);
			result = image;
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
	public byte[] getProductFease(@PathVariable Long id) {
		LOGGER.info("Call getProductFease");
		byte[] result = null;
		// TODO

		return result;
	}

	@GetMapping(value = "/{id}/market")
	@ResponseBody
	public byte[] getProductMarketingDoc(@PathVariable Long id) {
		LOGGER.info("Call getProductMarketingDoc");
		byte[] result = null;
		// TODO

		return result;
	}

	@GetMapping(value = "/{id}/tsheet")
	@ResponseBody
	public byte[] getProductTermSheet(@PathVariable Long id) {
		LOGGER.info("Call getProductTermSheet");
		byte[] result = null;
		// TODO

		return result;
	}

}
