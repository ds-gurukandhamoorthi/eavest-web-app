/**
 * 
 */
package com.synovia.digital.utils;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
public class EavControllerUtils {

	public static final long DISPLAY_MODAL_TIMEOUT_MS = 2 * 60 * 1000;

	public static final String VIEW_ERROR = "error";

	public static final String ATTR_ERROR_RESPONSE = "errorMessage";

	public static final String REQUEST_MAPPING_ERROR = "/error";

	public static final String I18N_ERROR_CODE = "err.msg";

	public static String createRedirectViewPath(String requestMapping) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		return redirectViewPath.toString();
	}

	public static String getIdentifiedName(EavAccount account) {
		StringBuilder identifiedName = new StringBuilder("");
		if (account != null) {
			identifiedName.append(account.getFirstName().substring(0, 1));
			identifiedName.append(". ");
			identifiedName.append(account.getLastName());
		}
		return identifiedName.toString();
	}

}
