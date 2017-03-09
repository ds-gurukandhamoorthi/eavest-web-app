/**
 * 
 */
package com.synovia.digital;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 21 févr. 2017
 */
public class TestUtil {

	public static final String MSG_NO_EXCEPTION_EXPECTED = "Should not have thrown an exception!";
	public static final String MSG_EXCEPTION_EXPECTED = "Should have thrown an exception!";

	private static final String CHARACTER = "a";

	public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public static String createRedirectViewPath(String path) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(path);
		return redirectViewPath.toString();
	}

	public static String createStringWithLength(int length) {
		StringBuilder builder = new StringBuilder();

		for (int index = 0; index < length; index++) {
			builder.append(CHARACTER);
		}

		return builder.toString();
	}
}
