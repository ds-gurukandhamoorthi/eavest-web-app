/**
 * 
 */
package com.synovia.digital.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
@Table(schema = "test", name = "prd_status")
@Entity
public class PrdStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CODE", nullable = false, unique = true, length = 20)
	private String code;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "prdStatus")
	private Collection<PrdProduct> prdProducts;

	/**
	 * Default Constructor.
	 */
	public PrdStatus() {

	}

	public PrdStatus(Integer id, String code) {
		this(id, code, null);
	}

	public PrdStatus(Integer id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}

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
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<PrdProduct> getPrdProducts() {
		return this.prdProducts;
	}

	public void setPrdProducts(Collection<PrdProduct> prdProducts) {
		this.prdProducts = prdProducts;
	}

}
