/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 20 mars 2017
 */
@Controller
@RequestMapping(value = "/products")
public class PrdProductController {

	public static final String VIEW_PRODUCTS = "products";

	@GetMapping()
	public String listProducts(Model model) {
		System.out.println("PrdProductController.listProducts()");
		return VIEW_PRODUCTS;
	}

}
