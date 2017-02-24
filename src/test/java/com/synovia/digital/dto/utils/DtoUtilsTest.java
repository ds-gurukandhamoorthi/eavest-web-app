/**
 * 
 */
package com.synovia.digital.dto.utils;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 24 févr. 2017
 */
public class DtoUtilsTest {

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.lang.String[])}.
	 */
	@Test
	public void testConvertAsListStringArray() {
		String date1 = "2000-12-08";
		String date2 = "2019-06-27";
		String date3 = "1980-05-26";
		String[] dates = new String[] { date1, date2, date3 };
		try {
			List<Date> result = DtoUtils.convertAsList(dates);
			int expectedSize = dates.length;
			Assert.assertEquals(expectedSize, result.size());
			for (String date : dates) {
				Assert.assertTrue(result.contains(DtoDateFormat.getFormat().parse(date)));
			}
		} catch (ParseException e) {
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.lang.String[])}.
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testConvertAsListStringArray_InvalidDateFormat() throws ParseException {
		String date1 = "2000/12/08";
		String[] dates = new String[] { date1 };
		DtoUtils.convertAsList(dates);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.lang.String[])}.
	 */
	@Test
	public void testConvertAsListStringArray_EmptyEntry() {
		String[] dates = new String[] {};
		try {
			List<Date> result = DtoUtils.convertAsList(dates);
			int expectedSize = dates.length;
			Assert.assertEquals(expectedSize, result.size());

		} catch (ParseException e) {
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.lang.String[])}.
	 */
	@Test
	public void testConvertAsListStringArray_NullEntry() {
		String[] dates = null;
		try {
			List<Date> result = DtoUtils.convertAsList(dates);
			Assert.assertNull(result);

		} catch (ParseException e) {
			fail("Should not have thrown an exception.");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.util.Date[])}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testConvertAsListDateArray() throws ParseException {
		Date date1 = DtoDateFormat.getFormat().parse("2000-12-08");
		Date date2 = DtoDateFormat.getFormat().parse("2019-06-27");
		Date date3 = DtoDateFormat.getFormat().parse("1980-05-26");
		Date[] dates = new Date[] { date1, date2, date3 };
		List<Date> result = DtoUtils.convertAsList(dates);
		int expectedSize = dates.length;
		Assert.assertEquals(expectedSize, result.size());
		for (Date date : dates) {
			Assert.assertTrue(result.contains(date));
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.util.Date[])}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testConvertAsListDateArray_EmptyEntry() {
		Date[] dates = new Date[] {};
		List<Date> result = DtoUtils.convertAsList(dates);
		int expectedSize = dates.length;
		Assert.assertEquals(expectedSize, result.size());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.dto.utils.DtoUtils#convertAsList(java.util.Date[])}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testConvertAsListDateArray_NullEntry() {
		Date[] dates = null;
		List<Date> result = DtoUtils.convertAsList(dates);
		Assert.assertNull(result);
	}

}
