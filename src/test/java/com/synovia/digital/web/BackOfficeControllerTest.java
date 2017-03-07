/**
 * 
 */
package com.synovia.digital.web;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.synovia.digital.TestUtil;
import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.model.ModelTest;

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
	 * {@link com.synovia.digital.web.BackOfficeController#showCreateProduct(org.springframework.ui.Model)}.
	 */
	@Test
	public void testShowCreateProductForm() {
		String expectedViewName = BackOfficeController.VIEW_CREATE_PRODUCT;
		try {
			// TEST : Expects a returned status OK, the view "create-product.html", model attributes "product" and "ssjacent" exists.
			mockMvc.perform(MockMvcRequestBuilders.get("/admin/createProduct")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.view().name(expectedViewName))
					.andExpect(model().attributeExists(BackOfficeController.ATTR_PRODUCT_DTO,
							BackOfficeController.ATTR_SOUS_JACENT_LIST, BackOfficeController.ATTR_PRODUCT_LIST));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#showCreateProduct(org.springframework.ui.Model)}.
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
	public void testCreateProduct() throws Exception {
		Long id = 1000001L;
		String isin = "AB12345678";
		String label = "Produit de test";
		String launchDate = "2014-03-12";
		String dueDate = "2022-03-12";

		// Expected values
		String expectedRedirectViewPath = TestUtil
				.createRedirectViewPath(BackOfficeController.REQUEST_MAPPING_ADD_PRODUCT_DATES);

		// Add a new entry
		mockMvc.perform(post("/admin/createProduct").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.PRODUCT_PARAM_ID, id.toString()).param(ModelTest.PRODUCT_PARAM_ISIN, isin)
				.param(ModelTest.PRODUCT_PARAM_LABEL, label).param(ModelTest.PRODUCT_PARAM_LAUNCH_DATE, launchDate)
				.param(ModelTest.PRODUCT_PARAM_DUE_DATE, dueDate)
				.requestAttr(BackOfficeController.ATTR_PRODUCT_DTO, new PrdProductDto()).with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name(expectedRedirectViewPath))
				.andExpect(model().attributeExists(BackOfficeController.PARAMETER_PRODUCT_ID))
				.andExpect(flash().attributeExists(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						BackOfficeController.ATTR_PRODUCT_LIST))
				.andExpect(flash().attribute(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						is("Product entry [AB12345678] has been successfully created!")));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addProduct(com.synovia.digital.model.PrdProduct, com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateProduct_DuplicateEntry() throws Exception {
		Long id = 1000001L;
		String isin = "FR12345678";
		String label = "Produit de test";
		String launchDate = "2014-03-12";
		String dueDate = "2022-03-12";

		// Add a new entry
		mockMvc.perform(post("/admin/createProduct").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.PRODUCT_PARAM_ID, id.toString()).param(ModelTest.PRODUCT_PARAM_ISIN, isin)
				.param(ModelTest.PRODUCT_PARAM_LABEL, label).param(ModelTest.PRODUCT_PARAM_LAUNCH_DATE, launchDate)
				.param(ModelTest.PRODUCT_PARAM_DUE_DATE, dueDate)
				.requestAttr(BackOfficeController.ATTR_PRODUCT_DTO, new PrdProductDto()).with(csrf()))
				.andExpect(status().is3xxRedirection());

		// Add a new entry
		mockMvc.perform(post("/admin/createProduct").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.PRODUCT_PARAM_ID, id.toString()).param(ModelTest.PRODUCT_PARAM_ISIN, isin)
				.param(ModelTest.PRODUCT_PARAM_LABEL, label).param(ModelTest.PRODUCT_PARAM_LAUNCH_DATE, launchDate)
				.param(ModelTest.PRODUCT_PARAM_DUE_DATE, dueDate)
				.requestAttr(BackOfficeController.ATTR_PRODUCT_DTO, new PrdProductDto()).with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name(BackOfficeController.VIEW_CREATE_PRODUCT))
				.andExpect(model().attribute(BackOfficeController.ATTR_PRODUCT_DTO, is(notNullValue())))
				.andExpect(model().attribute(BackOfficeController.ATTR_SOUS_JACENT_LIST, is(notNullValue())))
				.andExpect(model().attribute(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						is("Product entry [FR12345678] already exists!")));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addProduct(com.synovia.digital.model.PrdProduct, com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateProduct_InvalidEntry() throws Exception {
		Long id = 1000001L;
		String isin = "AB12345";
		String launchDate = "12/03/2014";
		String dueDate = "12/03/2022";

		// Add a new entry
		mockMvc.perform(post("/admin/createProduct").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.PRODUCT_PARAM_ID, id.toString()).param(ModelTest.PRODUCT_PARAM_ISIN, isin)
				.param(ModelTest.PRODUCT_PARAM_LAUNCH_DATE, launchDate).param(ModelTest.PRODUCT_PARAM_DUE_DATE, dueDate)
				.requestAttr(BackOfficeController.ATTR_PRODUCT_DTO, new PrdProductDto()).with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name(BackOfficeController.VIEW_CREATE_PRODUCT))
				.andExpect(model().attribute(BackOfficeController.ATTR_PRODUCT_DTO, is(notNullValue())))
				.andExpect(model().attribute(BackOfficeController.ATTR_SOUS_JACENT_LIST, is(notNullValue())))
				.andExpect(model().attribute(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						is("Product entry is incomplete!")));

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
					.andExpect(model().attributeExists(BackOfficeController.ATTR_SOUS_JACENT_DTO));
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
	public void testCreateSousJacent() throws Exception {
		Long id = 1000001L;
		String label = "NEW-SJCT-EXAMPLE";
		String bloombergCode = "BLOOMBEACH";

		// Expected values
		String expectedRedirectViewPath = TestUtil
				.createRedirectViewPath(BackOfficeController.REQUEST_MAPPING_CREATE_SSJACENT_VIEW);

		// Add a new entry
		mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.SOUS_JACENT_PARAM_LABEL, label).param(ModelTest.SOUS_JACENT_PARAM_ID, id.toString())
				.param(ModelTest.SOUS_JACENT_BLOOMBERG_CODE, bloombergCode)
				.requestAttr(BackOfficeController.ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto()).with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name(expectedRedirectViewPath))
				.andExpect(flash().attribute(BackOfficeController.ATTR_SOUS_JACENT_DTO, is(notNullValue())))
				.andExpect(flash().attribute(BackOfficeController.ATTR_SOUS_JACENT_LIST, is(notNullValue())))
				.andExpect(flash().attribute(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						is("Underlying asset entry [NEW-SJCT-EXAMPLE] was successfully created!")));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addSousJacent(com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateSousJacent_DuplicateEntry() throws Exception {
		// Add a new entry
		mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.SOUS_JACENT_PARAM_LABEL, "SJCT-EXAMPLE")
				.requestAttr(BackOfficeController.ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto()).with(csrf()))
				.andExpect(status().is3xxRedirection());

		// Duplicate the entry (identified by its label)
		mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.SOUS_JACENT_PARAM_LABEL, "SJCT-EXAMPLE")
				.requestAttr(BackOfficeController.ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto()).with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name(BackOfficeController.VIEW_CREATE_SSJACENT))
				.andExpect(model().attribute(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						is("Underlying asset entry [SJCT-EXAMPLE] already exists!")));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addSousJacent(com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateSousJacent_InvalidEntry() throws Exception {
		// Add a new entry
		mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.SOUS_JACENT_PARAM_LABEL, (String) null)
				.requestAttr(BackOfficeController.ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto()).with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name(BackOfficeController.VIEW_CREATE_SSJACENT))
				.andExpect(model().attribute(BackOfficeController.ATTR_MESSAGE_FEEDBACK,
						is("Underlying asset entry is incomplete!")));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.web.BackOfficeController#addSousJacent(com.synovia.digital.model.PrdSousJacent, org.springframework.ui.Model)}.
	 */
	@Test
	public void testCreateSousJacent_WithoutCSRFToken() throws Exception {
		mockMvc.perform(post("/admin/createSsjacent").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(ModelTest.SOUS_JACENT_PARAM_LABEL, "SJCT-EXAMPLE")
				.requestAttr(BackOfficeController.ATTR_SOUS_JACENT_DTO, new PrdSousjacentDto()))
				.andExpect(status().isForbidden());

	}

}
