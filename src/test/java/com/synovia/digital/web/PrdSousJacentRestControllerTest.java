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

import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdSousJacentValueRepository;

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
	private PrdSousJacentValueRepository valueRepo;

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

		this.valueRepo.deleteAll();
		this.repo.deleteAllInBatch();

		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-1")));
		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-2")));
		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-3")));
		this.sousJacentList.add(repo.save(new PrdSousJacent("SS-JCT-TEST-4")));
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
		// Test : Add a 1-param PrdSousJacent
		PrdSousjacentDto dto = new PrdSousjacentDto();
		dto.setLabel("CREATED-SS-JCT");
		dto.setBloombergCode("BLOOMBEACH");
		String prdSousJacentJson = json(dto);

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isCreated());

		int expectedSize = 5;
		Assert.assertEquals(expectedSize, repo.findAll().size());

		// Test : Add a 1-param PrdSousJacent
		dto = new PrdSousjacentDto();
		dto.setLabel("SS-JCT");
		dto.setBloombergCode("BLOOMBEACH2");
		prdSousJacentJson = json(dto);

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isCreated());

		expectedSize += 1;
		Assert.assertEquals(expectedSize, repo.findAll().size());

		// Test : Add a 1-param PrdSousJacent
		dto = new PrdSousjacentDto();
		dto.setLabel("created-ss-jct");
		dto.setBloombergCode("BLOOMBEACH3");
		prdSousJacentJson = json(dto);

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isCreated());

		expectedSize += 1;
		Assert.assertEquals(expectedSize, repo.findAll().size());

		// Test : Add an existing label PrdSousJacent
		dto = new PrdSousjacentDto();
		dto.setLabel("created-ss-jct");
		dto.setBloombergCode("BLOOMBEACH4");
		prdSousJacentJson = json(dto);

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isBadRequest());

		Assert.assertEquals(expectedSize, repo.findAll().size());
	}

	/**
	 * Tests the creation of a {@code PrdSousJacent} /api/prdSousJacents REST service
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreatePrdSousJacent_IncompleteEntry() throws Exception {
		// Test : Add an incomplete entry
		PrdSousjacentDto dto = new PrdSousjacentDto();
		String prdSousJacentJson = json(dto);

		this.mockMvc.perform(post("/api" + "/prdSousJacents").contentType(contentType).content(prdSousJacentJson))
				.andExpect(status().isBadRequest());

	}
}
