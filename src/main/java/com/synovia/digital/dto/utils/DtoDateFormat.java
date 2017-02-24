/**
 * 
 */
package com.synovia.digital.dto.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class defines utilities for date formatting.
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public class DtoDateFormat {

	private DtoDateFormat() {

	}

	/**
	 * The formatter for html input date. Format is yyyy-MM-dd
	 * 
	 * @return
	 */
	public static DateFormat getFormat() {
		return DtoDateFormatHolder.format;
	}

	private static class DtoDateFormatHolder {
		private final static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	}
}
