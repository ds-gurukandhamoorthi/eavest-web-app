package com.synovia.digital.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractPrdProductDate extends AbstractBean {

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

}
