package com.synovia.digital.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrdRule {
	// ------------------------------ FIELDS ------------------------------

	/** The protection barrier [%] */
	@Column(name = "PROTECTION_BARRIER")
	private Double protectionBarrier;

	/** The coupon barrier [%] */
	@Column(name = "COUPON_BARRIER")
	private Double couponBarrier;

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdRule() {

	}

	public PrdRule(Double protectionBarrier, Double couponBarrier) {
		this.protectionBarrier = protectionBarrier;
		this.couponBarrier = couponBarrier;

	}

	// --------------------- OTHER METHODS ---------------------

	@Override
	public String toString() {
		return new StringBuilder("[").append("Protection barrier:").append(protectionBarrier).append("; ")
				.append("Coupon barrier:").append(couponBarrier).append("]").toString();
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

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
