package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class PrdCouponDate extends AbstractPrdProductDate {

	public PrdCouponDate() {
		super();
	}

	public PrdCouponDate(Date date) {
		super(date);
	}

}
