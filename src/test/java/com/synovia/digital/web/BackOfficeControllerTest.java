/**
 * 
 */
package com.synovia.digital.web;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 6 févr. 2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@WithMockUser(roles = { "ADMIN" })
public class BackOfficeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#showBackOffice()}.
	 * 
	 */
	@Test
	public void testShowBackOffice() {
		String expectedViewName = BackOfficeController.VIEW_BACK_OFFICE;
		try {
			// TEST : Expects a returned status OK, the view "create-product.html", model attributes "product" and "ssjacent" exists.
			mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.view().name(expectedViewName));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#createProductForm(org.springframework.ui.Model)}.
	 */
	@Test
	public void testShowCreateProductForm() {
		String expectedViewName = BackOfficeController.VIEW_CREATE_PRODUCT;
		try {
			// TEST : Expects a returned status OK, the view "create-product.html", model attributes "product" and "ssjacent" exists.
			mockMvc.perform(MockMvcRequestBuilders.get("/admin/createProduct")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.view().name(expectedViewName))
					.andExpect(model().attributeExists("product", "ssjacent"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#createProductForm(org.springframework.ui.Model)}.
	 */
	@Test
	@WithAnonymousUser()
	public void testShowCreateProductForm_roleUSER() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/admin/createProduct")).andExpect(status().is3xxRedirection())
					.andExpect(redirectedUrl("http://localhost/login"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addProduct(com.synovia.digital.model.PrdProduct, com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateProduct() {
		Map<String, Object> sessionAttrs = new HashMap<>();
		sessionAttrs.put("product", new PrdProduct());
		sessionAttrs.put("ssjacent", new PrdSousJacent());
		try {
			// TEST : TODO Expects a returned status OK, the view "create-product.html", model attributes "product" and "ssjacent" exists.
			mockMvc.perform(post("/admin/createProduct").requestAttr("product", new PrdProduct())
					.requestAttr("ssjacent", new PrdSousJacent())).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#showCreateSousJacent(org.springframework.ui.Model)}.
	 * 
	 */
	@Test
	public void testShowCreateSousJacent() {
		String expectedViewName = BackOfficeController.VIEW_CREATE_SSJACENT;
		try {
			// TEST : Expects a returned status OK, the view "create-product.html", model attributes "product" and "ssjacent" exists.
			mockMvc.perform(MockMvcRequestBuilders.get("/admin/createSsjacent")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.view().name(expectedViewName))
					.andExpect(model().attributeExists("ssjacent"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addSousJacent(com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateSousJacent_EmptyParams() {
		try {
			// TEST : No parameters are sent to the controller ("ssjacent" is empty)
			mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.ALL).sessionAttr("ssJacent",
					new PrdSousJacent())).andExpect(status().isOk())
					.andExpect(view().name(BackOfficeController.VIEW_CREATE_SSJACENT));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addSousJacent(com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateSousJacent() {
		try {
			mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.APPLICATION_JSON)
					.param("label", "SJCT-EXAMPLE").param("value", "1000").requestAttr("ssjacent", new PrdSousJacent()))
					.andExpect(status().isOk());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

}
