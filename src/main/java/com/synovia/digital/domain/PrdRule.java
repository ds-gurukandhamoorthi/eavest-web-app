package com.synovia.digital.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Table(name="prd_rule", schema="eav")
@Table(name = "prd_rule", schema = "test")
@Entity
public class PrdRule extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The protection barrier [%] */
	@Column(name = "PROTECTION_BARRIER")
	private Double protectionBarrier;

	/** The coupon barrier [%] */
	@Column(name = "COUPON_BARRIER")
	private Double couponBarrier;

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdRule() {

	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getProtectionBarrier() {
		return this.protectionBarrier;
	}

	public void setProtectionBarrier(Double protectionBarrier) {
		this.protectionBarrier = protectionBarrier;
	}

	public Double getCouponBarrier() {
		return this.couponBarrier;
	}

	public void setCouponBarrier(Double couponBarrier) {
		this.couponBarrier = couponBarrier;
	}

}
