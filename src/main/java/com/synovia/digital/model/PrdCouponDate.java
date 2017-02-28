package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "prd_coupon_date", schema = "test")
public class PrdCouponDate extends AbstractPrdProductDate {

	public PrdCouponDate() {
		super();
	}

	public PrdCouponDate(Date date) {
		super(date);
	}

}
