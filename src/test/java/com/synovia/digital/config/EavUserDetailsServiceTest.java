/**
 * 
 */
package com.synovia.digital.config;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.synovia.digital.repository.EavAccountRepository;
import com.synovia.digital.spring.config.EavUserDetailsService;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 févr. 2017
 */
public class EavUserDetailsServiceTest {

	private EavUserDetailsService service;
	private EavAccountRepository repoMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		repoMock = mock(EavAccountRepository.class);
		service = new EavUserDetailsService(repoMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.spring.config.EavUserDetailsService#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsername() {
		fail("Not yet implemented");
	}

}
