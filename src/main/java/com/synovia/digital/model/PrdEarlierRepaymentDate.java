package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "prd_earlier_repayment_date", schema = "test")
public class PrdEarlierRepaymentDate extends AbstractPrdProductDate {

	public PrdEarlierRepaymentDate() {
		super();
	}

	public PrdEarlierRepaymentDate(Date date) {
		super(date);
	}

}
