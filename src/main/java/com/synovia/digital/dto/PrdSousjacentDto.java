/**
 * 
 */
package com.synovia.digital.dto;

import java.util.List;
import java.util.Set;

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

	/**
	 * The list of values in time ({@code PrdSousJacentValue} ids) of the underlying
	 * asset. <br />
	 */
	private List<Long> values;

	/** The list of products based on this underlying asset. */
	private Set<Long> products;

	public PrdSousjacentDto() {

	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Long> getValues() {
		return this.values;
	}

	public void setValues(List<Long> values) {
		this.values = values;
	}

	public Set<Long> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Long> products) {
		this.products = products;
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

}
