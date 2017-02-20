/**
 * 
 */
package com.synovia.digital.service;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 févr. 2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceImplTest {

	private EavAccount account;

	@Autowired
	private SubscriptionService service;

	@Before
	public void setUp() {
		this.account = new EavAccount("unittest@mailinator.com", "password", "TEST", "Unit");
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.SubscriptionServiceImpl#sendMailVerifyAccount(java.lang.String, com.synovia.digital.model.EavAccount)}.
	 */
	@Test
	public void testSendMailVerifyAccount() {
		try {
			service.sendMailVerifyAccount(account.getEmail(), account);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.SubscriptionServiceImpl#sendMailAccountCreated(java.lang.String, com.synovia.digital.model.EavAccount)}.
	 */
	@Test
	public void testSendMailAccountCreated() {
		try {
			service.sendMailAccountCreated(new String[] { "eavadm@mailinator.com" }, account);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.SubscriptionServiceImpl#sendMailAccountCreated(java.lang.String, com.synovia.digital.model.EavAccount)}.
	 */
	@Test
	public void testSendMailAccountCreated_MultipleAdmin() {
		try {
			service.sendMailAccountCreated(
					new String[] { "eavadm1@mailinator.com", "eavadm2@mailinator.com", "eavadm3@mailinator.com" },
					account);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.SubscriptionServiceImpl#sendMailResetPassword(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSendMailResetPassword() {
		try {
			service.sendMailResetPassword(account.getEmail(), "somefalsetoken");

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception.");
		}
	}

}
