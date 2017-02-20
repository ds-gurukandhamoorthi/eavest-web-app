package com.synovia.digital.model;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "prd_coupon_payment_dates", schema = "test")
@Entity
public class PrdCouponDate extends AbstractPrdProductDate {

	public PrdCouponDate() {
		super();
	}

	public PrdCouponDate(Integer seqNb, String date) throws ParseException {
		super(seqNb, date);
	}

}
