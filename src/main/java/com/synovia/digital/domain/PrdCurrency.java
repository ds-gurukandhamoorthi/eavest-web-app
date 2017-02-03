/**
 * 
 */
package com.synovia.digital.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 3 févr. 2017
 */
@Table(name = "prd_currency", schema = "test")
@Entity
public class PrdCurrency {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Length(max = 3)
	@Column(name = "CODE", unique = true)
	private String code;

	@Column(name = "LABEL")
	private String label;

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdCurrency() {

	}

	/**
	 * 
	 * Constructs a plain currency object based on a code and a label.
	 *
	 * @param code
	 * @param label
	 */
	public PrdCurrency(String code, String label) {
		this.setCode(code);
		this.label = label;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code.toUpperCase();
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
