package com.synovia.digital.domain;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "prd_watching_date", schema = "test")
@Entity
public class PrdWatchingDate extends AbstractPrdProductDate {

	public PrdWatchingDate() {
		super();
	}

	public PrdWatchingDate(Integer seqNb, String date) throws ParseException {
		super(seqNb, date);
	}

}
