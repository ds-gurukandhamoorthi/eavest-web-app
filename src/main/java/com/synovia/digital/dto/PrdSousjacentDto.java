/**
 * 
 */
package com.synovia.digital.dto;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 févr. 2017
 */
public class PrdSousjacentDto {

	private Long id;

	/** The label of the underlying asset. */
	@NotBlank
	@Size(max = 50)
	private String label;

	@Size(max = 25)
	private String isinCode;

	@Size(max = 25)
	private String bloombergCode;

	private Boolean isNew = true;

	private Boolean isPerfReview = false;

	public PrdSousjacentDto() {

	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getIsinCode() {
		return this.isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public Boolean getIsNew() {
		return this.isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getBloombergCode() {
		return this.bloombergCode;
	}

	public void setBloombergCode(String bloombergCode) {
		this.bloombergCode = bloombergCode;
	}

	public Boolean getIsPerfReview() {
		return this.isPerfReview;
	}

	public void setIsPerfReview(Boolean isPerfReview) {
		this.isPerfReview = isPerfReview;
	}

}
