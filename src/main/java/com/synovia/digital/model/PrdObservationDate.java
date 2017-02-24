package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class PrdObservationDate extends AbstractPrdProductDate {

	public PrdObservationDate() {
		super();
	}

	public PrdObservationDate(Date date) {
		super(date);
	}

}
