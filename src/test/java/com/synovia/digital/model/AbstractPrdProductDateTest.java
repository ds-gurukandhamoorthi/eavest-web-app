/**
 * 
 */
package com.synovia.digital.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 5 mars 2017
 */
public class AbstractPrdProductDateTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.model.AbstractPrdProductDate#toString()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testToString() throws ParseException {
		AbstractPrdProductDate obsDate = new AbstractPrdProductDate() {
		};
		Long id = 5L;
		String dateAsString = "22/12/2001";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);

		PrdProduct prdProduct = new PrdProduct();
		Long idPrdProduct = 17L;
		prdProduct.setId(idPrdProduct);

		obsDate.setId(id);
		obsDate.setDate(date);
		obsDate.setPrdProduct(prdProduct);

		String toTest = obsDate.toString();
		Assert.assertTrue("Should contain the id", toTest.contains(id.toString()));
		Assert.assertTrue("Should contain the date", toTest.contains(dateAsString));
		Assert.assertTrue("Should contain the prdProduct entity", toTest.contains(idPrdProduct.toString()));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.model.AbstractPrdProductDate#toString()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testToString_IncompleteEntity_IdNull() throws ParseException {
		AbstractPrdProductDate obsDate = new AbstractPrdProductDate() {
		};
		Long id = null;
		String dateAsString = "22/12/2001";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);

		PrdProduct prdProduct = new PrdProduct();
		Long idPrdProduct = 17L;
		prdProduct.setId(idPrdProduct);

		obsDate.setId(id);
		obsDate.setDate(date);
		obsDate.setPrdProduct(prdProduct);

		String toTest = obsDate.toString();
		Assert.assertTrue("Should contain the date", toTest.contains(dateAsString));
		Assert.assertTrue("Should contain the prdProduct entity", toTest.contains(idPrdProduct.toString()));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.model.AbstractPrdProductDate#toString()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testToString_IncompleteEntity_DateNull() throws ParseException {
		AbstractPrdProductDate obsDate = new AbstractPrdProductDate() {
		};
		Long id = 5L;
		Date date = null;

		PrdProduct prdProduct = new PrdProduct();
		Long idPrdProduct = 17L;
		prdProduct.setId(idPrdProduct);

		obsDate.setId(id);
		obsDate.setDate(date);
		obsDate.setPrdProduct(prdProduct);

		String toTest = obsDate.toString();
		Assert.assertTrue("Should contain the id", toTest.contains(id.toString()));
		Assert.assertTrue("Should contain the prdProduct entity", toTest.contains(idPrdProduct.toString()));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.model.AbstractPrdProductDate#toString()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testToString_IncompleteEntity_PrdProductNull() throws ParseException {
		AbstractPrdProductDate obsDate = new AbstractPrdProductDate() {
		};
		Long id = 5L;
		String dateAsString = "22/12/2001";
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);
		PrdProduct prdProduct = null;

		obsDate.setId(id);
		obsDate.setDate(date);
		obsDate.setPrdProduct(prdProduct);

		String toTest = obsDate.toString();
		Assert.assertTrue("Should contain the id", toTest.contains(id.toString()));
		Assert.assertTrue("Should contain the date", toTest.contains(dateAsString));
	}
}
