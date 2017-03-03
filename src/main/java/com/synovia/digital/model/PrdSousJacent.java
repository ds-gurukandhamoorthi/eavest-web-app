package com.synovia.digital.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "prd_sousjacent", schema = "test")
@Entity
public class PrdSousJacent extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "LABEL", nullable = false, unique = true, length = 50)
	private String label;

	@Column(name = "ISIN_CODE", unique = true, length = 25)
	private String isinCode;

	@Column(name = "IS_NEW")
	private Boolean isNew = true;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prdSousJacent")
	private List<PrdSousJacentValue> prdSousJacentValues;

	@OneToMany(mappedBy = "prdSousJacent")
	private Set<PrdProduct> prdProducts;

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdSousJacent() {
		this.prdProducts = new HashSet<>();
		this.prdSousJacentValues = new ArrayList<>();

	}

	public PrdSousJacent(String label) {
		this.label = label;
	}

	public PrdSousJacent(String label, List<PrdSousJacentValue> values) {
		this.label = label;
		this.prdSousJacentValues = values;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<PrdProduct> getPrdProducts() {
		return this.prdProducts;
	}

	public void setPrdProducts(Set<PrdProduct> prdProducts) {
		this.prdProducts = prdProducts;
	}

	public List<PrdSousJacentValue> getPrdSousJacentValues() {
		return this.prdSousJacentValues;
	}

	public void setPrdSousJacentValues(List<PrdSousJacentValue> prdSousJacentValues) {
		this.prdSousJacentValues = prdSousJacentValues;
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

}
