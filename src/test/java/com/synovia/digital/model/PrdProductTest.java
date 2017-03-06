/**
 * 
 */
package com.synovia.digital.model;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 5 mars 2017
 */
public class PrdProductTest {

	private Long id;
	private String isin;
	private String label;
	private String launchDateAsString;
	private String dueDateAsString;
	private Long prdSousJacentId;
	private Double protectionBarrier;
	private Double couponBarrier;
	private String subscriptionStartDateAsString;
	private String subscriptionEndDateAsString;
	private Double couponValue;
	private Double nominalValue;
	private Boolean capitalGuaranteed;
	private Double startPrice;
	private String deliver;
	private String guarantor;
	private Integer prdStatusId;
	private String endDateAsString;
	private Boolean isEavest;
	private Boolean isBestSeller;
	private String path;
	private Double strike;
	private String observationFrequency;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		id = 2345L;
		isin = "ISIN23456";
		label = "PRODUCT-TEST";
		launchDateAsString = "25/12/1999";
		dueDateAsString = "12/07/2010";
		prdSousJacentId = 947L;
		protectionBarrier = 54d;
		couponBarrier = 3456d;
		subscriptionEndDateAsString = "06/07/2045";
		subscriptionStartDateAsString = "17/05/2014";
		couponValue = 67d;
		nominalValue = 345.8;
		capitalGuaranteed = true;
		startPrice = 456.09876;
		deliver = "BANK NAME TEST";
		guarantor = "GUARANT TEST";
		prdStatusId = 8654;
		endDateAsString = "06/04/2099";
		isEavest = false;
		isBestSeller = true;
		path = "//path/dir";
		strike = 3456.;
		observationFrequency = "tous les jours";
	}

	/**
	 * Test method for {@link com.synovia.digital.model.PrdProduct#toString()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testToString() throws ParseException {
		PrdProduct product = new PrdProduct();
		product.setId(id);
		product.setIsin(isin);
		product.setLabel(label);
		product.setLaunchDateAsString(launchDateAsString);
		product.setDueDateAsString(dueDateAsString);
		PrdSousJacent base = new PrdSousJacent();
		base.setId(prdSousJacentId);
		product.setPrdSousJacent(base);
		product.setPrdRule(new PrdRule(protectionBarrier, couponBarrier));
		product.setSubscriptionStartDateAsString(subscriptionStartDateAsString);
		product.setSubscriptionEndDateAsString(subscriptionEndDateAsString);
		product.setCouponValue(couponValue);
		product.setNominalValue(nominalValue);
		product.setCapitalGuaranteed(capitalGuaranteed);
		product.setStartPrice(startPrice);
		product.setDeliver(deliver);
		product.setGuarantor(guarantor);
		PrdStatus prdStatus = new PrdStatus();
		prdStatus.setId(prdStatusId);
		product.setPrdStatus(prdStatus);
		product.setEndDateAsString(endDateAsString);
		product.setIsBestSeller(isEavest);
		product.setIsBestSeller(isBestSeller);
		product.setPath(path);
		product.setStrike(strike);
		product.setObservationFrequency(observationFrequency);

		String result = product.toString();

		Assert.assertTrue(result.contains(id.toString()));
		Assert.assertTrue(result.contains(isin));
		Assert.assertTrue(result.contains(label));
		Assert.assertTrue(result.contains(launchDateAsString));
		Assert.assertTrue(result.contains(dueDateAsString));
		Assert.assertTrue(result.contains(prdSousJacentId.toString()));
		Assert.assertTrue(result.contains(protectionBarrier.toString()));
		Assert.assertTrue(result.contains(protectionBarrier.toString()));
		Assert.assertTrue(result.contains(couponBarrier.toString()));
		Assert.assertTrue(result.contains(subscriptionStartDateAsString));
		Assert.assertTrue(result.contains(subscriptionEndDateAsString));
		Assert.assertTrue(result.contains(couponValue.toString()));
		Assert.assertTrue(result.contains(nominalValue.toString()));
		Assert.assertTrue(result.contains(capitalGuaranteed.toString()));
		Assert.assertTrue(result.contains(startPrice.toString()));
		Assert.assertTrue(result.contains(deliver));
		Assert.assertTrue(result.contains(guarantor));
		Assert.assertTrue(result.contains(prdStatusId.toString()));
		Assert.assertTrue(result.contains(endDateAsString));
		Assert.assertTrue(result.contains(isEavest.toString()));
		Assert.assertTrue(result.contains(isBestSeller.toString()));
		Assert.assertTrue(result.contains(path));
		Assert.assertTrue(result.contains(strike.toString()));
		Assert.assertTrue(result.contains(observationFrequency));
	}

	/**
	 * Test method for {@link com.synovia.digital.model.PrdProduct#toString()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testToString_NullParams() throws ParseException {
		PrdProduct product = new PrdProduct();
		product.setId(null);
		product.setIsin(null);
		product.setLabel(null);
		product.setLaunchDate(null);
		product.setDueDate(null);
		product.setPrdSousJacent(null);
		product.setPrdRule(null);
		product.setSubscriptionStartDate(null);
		product.setSubscriptionEndDate(null);
		product.setCouponValue(null);
		product.setNominalValue(null);
		product.setCapitalGuaranteed(null);
		product.setStartPrice(null);
		product.setDeliver(null);
		product.setGuarantor(null);
		product.setPrdStatus(null);
		product.setEndDate(null);
		product.setIsBestSeller(null);
		product.setIsBestSeller(null);
		product.setPath(null);
		product.setStrike(null);
		product.setObservationFrequency(null);

		try {
			product.toString();

		} catch (Exception e) {
			Assert.fail("Should not have thrown an exception");
		}

	}

}
