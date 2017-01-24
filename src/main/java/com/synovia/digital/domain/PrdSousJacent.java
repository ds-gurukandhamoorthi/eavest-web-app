package com.synovia.digital.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//@Table(name="prd_sousjacent", schema="eav")
@Table(name = "prd_sousjacent", schema = "test")
@Entity
public class PrdSousJacent extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ISIN", nullable = false, unique = true)
	private String isin;

	@Column(name = "CURRENCY_ISO", length = 3)
	private String currency;

	@Column(name = "LABEL")
	private String label;

	@Column(name = "VALUE")
	private Double value;

	@Column(name = "DATE")
	private Date date;

	@OneToMany(mappedBy = "idPrdSousJacent")
	private Set<PrdProduct> prdProducts;

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdSousJacent() {

	}

	public PrdSousJacent(String isinCode, String label, String currency) {
		this.isin = isinCode;
		this.label = label;
		this.currency = currency;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsin() {
		return this.isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String devise) {
		this.currency = devise;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<PrdProduct> getPrdProducts() {
		return this.prdProducts;
	}

	public void setPrdProducts(Set<PrdProduct> prdProducts) {
		this.prdProducts = prdProducts;
	}

}
