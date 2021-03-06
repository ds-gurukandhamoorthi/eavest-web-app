/**
 * 
 */
package com.synovia.digital.exceptions;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public abstract class EavException extends Exception {

	/**  */
	private static final long serialVersionUID = 1L;

	protected String code;

	public EavException(String code) {
		this(code, null);
	}

	public EavException(String code, Throwable t) {
		// TODO Translate the code
		super(code, t);
		this.code = code;

	}

	public String getCode() {
		return this.code;
	}

}
