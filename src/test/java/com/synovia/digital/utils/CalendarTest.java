/**
 * 
 */
package com.synovia.digital.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 6 mars 2017
 */
public class CalendarTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSet_YearMonthDay() {
		Calendar c = Calendar.getInstance();

		int year = 2012;
		int month = 0;
		int date = 22;

		c.set(year, month, date);

		System.out.println("c.toString=" + c.toString());
		String d = new SimpleDateFormat("dd.MM.yy").format(c.getTime());
		System.out.println("formatted date=" + d);
	}

	@Test
	public void testSet_UseCase() {
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentYear = calendar.get(Calendar.YEAR);
		Integer displayedYear = currentYear - EavConstants.C_MILLENARY;
		calendar.set(Calendar.YEAR, currentYear - 1);
		calendar.set(Calendar.MONTH, currentMonth - 1);
		int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
		String oneYearBefore = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());

		System.out.println("currentMonth=" + currentMonth);
		System.out.println("currentYear=" + currentYear);
		System.out.println("Displayed year=" + displayedYear);
		System.out.println("One year past=" + oneYearBefore);
	}
}
