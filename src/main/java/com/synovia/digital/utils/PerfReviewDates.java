/**
 * 
 */
package com.synovia.digital.utils;

import java.text.DateFormat;
import java.util.Calendar;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 20 mars 2017
 */
public class PerfReviewDates {

	protected Calendar lastDayOfLastMonth;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public PerfReviewDates() {
		lastDayOfLastMonth = Calendar.getInstance(LocaleContextHolder.getLocale());

		int idxCurrentMonth = lastDayOfLastMonth.get(Calendar.MONTH);
		int currentYear = lastDayOfLastMonth.get(Calendar.YEAR);
		lastDayOfLastMonth.set(currentYear, idxCurrentMonth - 1, 1);
		int maxDayOfMonth = lastDayOfLastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDayOfLastMonth.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);

	}

	public String getLastDayOfLastMonthAsString(DateFormat formatter) {
		return formatter.format(lastDayOfLastMonth.getTime());
	}
}
