/**
 * 
 */
package com.synovia.digital.utils;

import java.text.DateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 20 mars 2017
 */
public class PerfReviewDates {

	protected Calendar lastDayOfLastMonth;
	protected Calendar lastDayOfPreviousMonth;
	protected Calendar lastDayOfLastMonthOfLastYear;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public PerfReviewDates() {
		lastDayOfLastMonth = Calendar.getInstance(LocaleContextHolder.getLocale());

		int idxCurrentMonth = lastDayOfLastMonth.get(Calendar.MONTH);
		int currentYear = lastDayOfLastMonth.get(Calendar.YEAR);
		lastDayOfLastMonth.set(currentYear, idxCurrentMonth - 1, 1, 0, 0, 0);
		long roundedTimeMs = (long) Math.floor(lastDayOfLastMonth.getTimeInMillis() / 1000) * 1000;
		lastDayOfLastMonth.setTimeInMillis(roundedTimeMs);
		int maxDayOfMonth = lastDayOfLastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDayOfLastMonth.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);

		lastDayOfLastMonthOfLastYear = Calendar.getInstance(LocaleContextHolder.getLocale());
		lastDayOfLastMonthOfLastYear.setTime(DateUtils.addYears(lastDayOfLastMonth.getTime(), -1));
		maxDayOfMonth = lastDayOfLastMonthOfLastYear.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDayOfLastMonthOfLastYear.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);

		lastDayOfPreviousMonth = Calendar.getInstance(LocaleContextHolder.getLocale());
		lastDayOfPreviousMonth.setTime(DateUtils.addMonths(lastDayOfLastMonth.getTime(), -1));
		maxDayOfMonth = lastDayOfPreviousMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDayOfPreviousMonth.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
	}

	public Calendar getLastDayOfPreviousMonth() {
		return this.lastDayOfPreviousMonth;
	}

	public String getLastDayOfLastMonthAsString(DateFormat formatter) {
		return formatter.format(lastDayOfLastMonth.getTime());
	}

	public Calendar getLastDayOfLastMonth() {
		return lastDayOfLastMonth;
	}

	public Calendar getLastDayOfLastMonthOfLastYear() {
		return this.lastDayOfLastMonthOfLastYear;
	}

	/**
	 * Gets the last day of specified month in current year
	 * 
	 * @param idxMonth
	 *            The index of the month. Starts at 0.
	 * @return
	 */
	public Calendar getLastDayOfMonth(int idxMonth) {
		Calendar c = Calendar.getInstance(LocaleContextHolder.getLocale());

		int currentYear = c.get(Calendar.YEAR);
		c.set(currentYear, idxMonth, 1);
		int maxDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);

		return c;
	}
}
