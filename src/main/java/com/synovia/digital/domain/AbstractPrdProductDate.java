package com.synovia.digital.domain;

import java.text.DateFormat;
import java.text.ParseException;
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
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractPrdProductDate extends AbstractBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SEQUENCE_NB")
	private Integer sequenceNumber;

	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "ID_PRD_PRODUCT", referencedColumnName = "ID", nullable = false)
	@NotNull
	private PrdProduct idPrdProduct;

	@Transient
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public AbstractPrdProductDate() {
	}

	public AbstractPrdProductDate(Integer seqNb, Date date) {
		this.date = date;
		this.sequenceNumber = seqNb;
	}

	public AbstractPrdProductDate(Integer seqNb, String date) throws ParseException {
		this.date = format.parse(date);
		this.sequenceNumber = seqNb;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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

	public PrdProduct getIdPrdProduct() {
		return this.idPrdProduct;
	}

	public void setIdPrdProduct(PrdProduct idPrdProduct) {
		this.idPrdProduct = idPrdProduct;
	}

}
