/**
 * 
 */
package com.synovia.digital.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 18 févr. 2017
 */
public class EavConstraintViolationEntry extends EavTechnicalException {

	/**  */
	private static final long serialVersionUID = 1L;

	protected static final String ERROR_CODE_PREFIX = "constraint.violation.";

	protected Set<ConstraintViolation<Object>> constraintViolations;

	/**
	 * TODO Constructs ... based on ...
	 *
	 * @param code
	 */
	public EavConstraintViolationEntry(String modelName, Set<ConstraintViolation<Object>> violations) {
		super(new StringBuilder(ERROR_CODE_PREFIX).append(modelName).toString());
		this.constraintViolations = violations;
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return this.constraintViolations;
	}

}
