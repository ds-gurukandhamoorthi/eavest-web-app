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
public class EavDuplicateEntryException extends EavTechnicalException {

	/**  */
	private static final long serialVersionUID = 1L;
	public static final String ERROR_CODE_PREFIX = "duplicate.entry.";

	/**
	 * TODO Constructs ... based on ...
	 *
	 * @param code
	 */
	public EavDuplicateEntryException(String modelName) {
		super(new StringBuilder(ERROR_CODE_PREFIX).append(modelName).toString());
	}

}
