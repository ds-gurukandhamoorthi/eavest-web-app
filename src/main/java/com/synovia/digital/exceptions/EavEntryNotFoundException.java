/**
 * 
 */
package com.synovia.digital.exceptions;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 18 févr. 2017
 */
public class EavEntryNotFoundException extends EavTechnicalException {

	/**  */
	private static final long serialVersionUID = 1L;
	public static final String ERROR_CODE_PREFIX = "entry.not.found.";

	/**
	 * TODO Constructs ... based on ...
	 *
	 * @param code
	 */
	public EavEntryNotFoundException(String modelName) {
		super(new StringBuilder(ERROR_CODE_PREFIX).append(modelName).toString());
	}

}
