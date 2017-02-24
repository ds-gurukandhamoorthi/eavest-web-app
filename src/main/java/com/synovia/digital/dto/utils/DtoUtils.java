/**
 * 
 */
package com.synovia.digital.dto.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 24 févr. 2017
 */
public class DtoUtils {

	public static List<Date> convertAsList(String... dates) throws ParseException {
		List<Date> result = null;
		if (dates != null) {
			result = new ArrayList<>();
			for (String date : dates) {
				result.add(DtoDateFormat.getFormat().parse(date));
			}
		}
		return result;

	}

	public static List<Date> convertAsList(Date... dates) {
		List<Date> result = null;
		if (dates != null) {
			result = new ArrayList<>();
			for (Date date : dates) {
				result.add(date);
			}
		}
		return result;

	}

}
