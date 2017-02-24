package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class PrdEarlierRepaymentDate extends AbstractPrdProductDate {

	public PrdEarlierRepaymentDate() {
		super();
	}

	public PrdEarlierRepaymentDate(Date date) {
		super(date);
	}

}
