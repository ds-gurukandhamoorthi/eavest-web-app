package com.synovia.digital.model;

import java.text.ParseException;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "prd_observation_date", schema = "test")
@Entity
public class PrdObservationDate extends AbstractPrdProductDate {

	public PrdObservationDate() {
		super();
	}

	public PrdObservationDate(Integer seqNb, String date) throws ParseException {
		super(seqNb, date);
	}

}
