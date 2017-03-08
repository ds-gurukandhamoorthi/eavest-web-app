/**
 * 
 */
package com.synovia.digital.model;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 mars 2017
 */
public class PrdSousJacentTest {

	private Long id;

	private String label;

	private String isinCode;

	private String bloombergCode;

	private Boolean isNew;

	private Boolean isPerfReview;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		id = 46L;
		label = "SSJCT-TEST";
		isinCode = "ISINI-TEST";
		bloombergCode = "BLOOM-BEACH-AHHAHA";
		isNew = false;
		isPerfReview = true;
	}

	/**
	 * Test method for {@link com.synovia.digital.model.PrdSousJacent#toString()}.
	 */
	@Test
	public void testToString() {
		PrdSousJacent entity = new PrdSousJacent();

		entity.setId(id);
		entity.setLabel(label);
		entity.setIsinCode(isinCode);
		entity.setBloombergCode(bloombergCode);
		entity.setIsNew(isNew);
		entity.setIsPerfReview(isPerfReview);

		String result = entity.toString();

		Assert.assertTrue(result.contains(id.toString()));
		Assert.assertTrue(result.contains(label));
		Assert.assertTrue(result.contains(isinCode));
		Assert.assertTrue(result.contains(bloombergCode));
		Assert.assertTrue(result.contains(isNew.toString()));
		Assert.assertTrue(result.contains(isPerfReview.toString()));

	}

	/**
	 * Test method for {@link com.synovia.digital.model.PrdSousJacent#toString()}.
	 */
	@Test
	public void testToString_NullValues() {
		PrdSousJacent entity = new PrdSousJacent();

		entity.setId(null);
		entity.setLabel(null);
		entity.setIsinCode(null);
		entity.setBloombergCode(null);
		entity.setIsNew(null);
		entity.setIsPerfReview(null);

		try {
			entity.toString();

		} catch (Exception e) {
			fail("Should not have thrown an exception.");
		}
	}

}
