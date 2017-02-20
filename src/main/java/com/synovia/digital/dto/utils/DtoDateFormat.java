/**
 * 
 */
package com.synovia.digital.dto.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public class DtoDateFormat {

	private DtoDateFormat() {

	}

	public static DateFormat getFormat() {
		return DtoDateFormatHolder.format;
	}

	private static class DtoDateFormatHolder {
		private final static DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	}
}
