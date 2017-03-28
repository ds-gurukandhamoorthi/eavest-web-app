/**
 * 
 */
package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class defines the bean for the value in time of an underlying asset.
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
@Entity
@Table(name = "prd_sousjacent_value", schema = "test")
public class PrdSousJacentValue extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_PRD_SOUSJACENT", nullable = false, updatable = false)
	private PrdSousJacent prdSousJacent;

	@Column(name = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	/**
	 * Value of the underlying asset [EUR].
	 */
	@Column(name = "VALUE", nullable = false)
	private Double value;

	// --------------------- CONSTRUCTOR(S) ---------------------
	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public PrdSousJacentValue() {
		this(null, null, null);
	}

	public PrdSousJacentValue(PrdSousJacent ssjct, Date d, Double value) {
		this.prdSousJacent = ssjct;
		this.date = d;
		this.value = value;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PrdSousJacent getPrdSousJacent() {
		return this.prdSousJacent;
	}

	public void setPrdSousJacent(PrdSousJacent prdSousJacent) {
		this.prdSousJacent = prdSousJacent;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
