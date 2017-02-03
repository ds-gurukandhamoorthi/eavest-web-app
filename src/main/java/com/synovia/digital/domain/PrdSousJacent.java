package com.synovia.digital.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "prd_sousjacent", schema = "test")
@Entity
public class PrdSousJacent extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "CURRENCY_ISO", length = 3)
	private String currency = "EUR";

	@Column(name = "LABEL")
	private String label;

	@Column(name = "VALUE")
	private Double value;

	@Column(name = "DATE")
	private Date date = new Date();

	@OneToMany(mappedBy = "idPrdSousJacent")
	private Set<PrdProduct> prdProducts;

	@Transient
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdSousJacent() {

	}

	public PrdSousJacent(String label, Double value) {
		this.label = label;
		this.value = value;
	}

	public PrdSousJacent(String label, Double value, String date) throws ParseException {
		this.label = label;
		this.value = value;
		this.date = format.parse(date);
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
