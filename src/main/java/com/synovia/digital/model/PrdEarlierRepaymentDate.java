package com.synovia.digital.model;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "prd_earlier_repayment_date", schema = "test")
@Entity
public class PrdEarlierRepaymentDate extends AbstractPrdProductDate {

	public PrdEarlierRepaymentDate() {
		super();
	}

	public PrdEarlierRepaymentDate(Integer seqNb, String date) throws ParseException {
		super(seqNb, date);
	}

}
