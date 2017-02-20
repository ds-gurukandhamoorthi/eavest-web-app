/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.model.PrdSousJacent;
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

	public static String VIEW_CREATE_PRODUCT = "create-product";
	public static String VIEW_CREATE_SSJACENT = "create-ssjacent";
	public static String VIEW_BACK_OFFICE = "back-office";

	@Autowired
	protected PrdSousJacentService sousJacentService;

	@RequestMapping()
	public String showBackOffice() {
		return VIEW_BACK_OFFICE;
	}

	@GetMapping(value = "/createProduct")
	public String createProductForm(Model model) {
		System.out.println("BackOfficeController.createProductForm()");
		model.addAttribute("product", new PrdProductDto());
		model.addAttribute("ssjacent", new PrdSousjacentDto());
		return VIEW_CREATE_PRODUCT;
	}

	@PostMapping(value = "/createProduct")
	public String addProduct(@ModelAttribute PrdProductDto product, @ModelAttribute PrdSousjacentDto ssJacent,
			Model model) {
		System.out.println("BackOfficeController.addProduct()");
		model.addAttribute("product", product);
		model.addAttribute("ssjacent", ssJacent);

		// TODO
		sousJacentService.add(sousJacentDto);

		repo.save(product);

		return "create-product";
	}

	@GetMapping(value = "/createSsjacent")
	public String showCreateSousJacent(Model model) {
		System.out.println("BackOfficeController.showCreateSousJacent()");
		model.addAttribute("ssjacent", new PrdSousJacent());
		//		ssjacentRepo.save(ssJacent);
		return VIEW_CREATE_SSJACENT;

	}

	@PostMapping(value = "/createSsjacent")
	public String createSousJacent(@ModelAttribute PrdSousJacent ssJacent, Model model) {
		System.out.println("BackOfficeController.createSousJacent()");
		model.addAttribute("ssjacent", ssJacent);

		ssjacentRepo.save(ssJacent);
		return VIEW_CREATE_SSJACENT;
	}

}
