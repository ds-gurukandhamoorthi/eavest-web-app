/**
 * 
 */
package com.synovia.digital.filedataware;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 8 mars 2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EavConfigAppTest {

	@Autowired
	private EavHomeDirectory homeDir;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConfigEavHomeDirectory() {
		Assert.assertNotNull(homeDir);
		try {
			Assert.assertTrue(homeDir.getPath().contains("FDWH"));

		} catch (Exception e) {
			Assert.fail("Should not have thrown an exception.");
		}
	}

}
