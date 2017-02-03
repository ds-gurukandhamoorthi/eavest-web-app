/**
 * 
 */
package com.synovia.digital.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.synovia.digital.domain.PrdSousJacent;
import com.synovia.digital.repository.PrdSousJacentRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 2 févr. 2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PrdSousJacentRestControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	private MockMvc mockMvc;

	private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

	@Autowired
	private PrdSousJacentRepository repo;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private List<PrdSousJacent> sousJacentList = new ArrayList<>();

	@Autowired
	void setConverters(HttpMessageConverter<Object>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		this.repo.deleteAllInBatch();

		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-1", 100000.)));
		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-2", 200000., "01/01/2017")));
		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-3", 300000., "01/12/2016")));
		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-4", 400000., "01/11/2016")));
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	/**
	 * Tests the creation of a {@code PrdSousJacent} /api/prdSousJacents REST service
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreatePrdSousJacent() throws Exception {
		// Test : Add a 2-params PrdSousJacent
		String prdSousJacentJson = json(new PrdSousJacent("CREATED-SS-JCT", 120.));

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isCreated());

		// Test : Add a 2-params PrdSousJacent
		prdSousJacentJson = json(new PrdSousJacent("CREATED-SS-JCT", 120., "01/12/2016"));

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isCreated());
	}

	/**
	 * Tests the creation of a {@code PrdSousJacent} /api/prdSousJacents REST service
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreatePrdSousJacent_defaultConstructor() throws Exception {
		// Test : Add a 2-params PrdSousJacent
		String prdSousJacentJson = json(new PrdSousJacent());

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isCreated());

	}
}
