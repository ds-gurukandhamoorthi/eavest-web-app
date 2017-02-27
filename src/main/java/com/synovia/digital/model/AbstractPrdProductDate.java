package com.synovia.digital.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractPrdProductDate extends AbstractBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_PRD_PRODUCT", nullable = false)
	private PrdProduct prdProduct;

	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Transient
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public AbstractPrdProductDate() {
	}

	public AbstractPrdProductDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DateFormat getFormat() {
		return this.format;
	}

	public void setFormat(DateFormat format) {
		this.format = format;
	}

	public PrdProduct getPrdProduct() {
		return this.prdProduct;
	}

	public void setPrdProduct(PrdProduct prdProduct) {
		this.prdProduct = prdProduct;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
