/**
 * 
 */
package com.synovia.digital.dto.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.synovia.digital.utils.EavUtils;

/**
 * This class defines utilities for date formatting from DTO. <br/>
 * The format of the date from the front-end is "yyyy-MM-dd".
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
		private final static DateFormat format = new SimpleDateFormat(EavUtils.DTO_DATE_FORMAT_PATTERN);
	}
}
