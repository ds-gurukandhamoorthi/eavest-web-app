/**
 * 
 */
package com.synovia.digital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.synovia.digital.domain.PrdProduct;
import com.synovia.digital.domain.PrdSousJacent;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 31 janv. 2017
 */
@Controller
@RequestMapping(value = "/admin")
public class BackOfficeController {

	@Autowired
	protected PrdProductRepository repo;

	@Autowired
	protected PrdSousJacentRepository ssjacentRepo;

	@GetMapping(value = "/createProduct")
	public String createProductForm(Model model) {
		System.out.println("BackOfficeController.createProductForm()");
		model.addAttribute("product", new PrdProduct());
		model.addAttribute("ssjacent", new PrdSousJacent());
		return "create-product";
	}

	@PostMapping(value = "/createProduct")
	public String createProduct(@ModelAttribute PrdProduct product, @ModelAttribute PrdSousJacent ssJacent,
			Model model) {
		System.out.println("BackOfficeController.createProduct()");
		model.addAttribute("product", product);
		model.addAttribute("ssjacent", ssJacent);

		product.setIdPrdSousJacent(ssJacent);
		ssJacent.getPrdProducts().add(product);

		repo.save(product);

		return "create-product";
	}

	@RequestMapping(value = "/createSsjacent", method = RequestMethod.POST)
	public ResponseEntity<?> createSousJacent(@ModelAttribute PrdSousJacent ssJacent, Model model) {
		System.out.println("BackOfficeController.createSousJacent()");
		model.addAttribute("ssjacent", ssJacent);
		ssjacentRepo.save(ssJacent);

		return null;
	}
}
