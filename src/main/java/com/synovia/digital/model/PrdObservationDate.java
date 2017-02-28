package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "prd_observation_date", schema = "test")
public class PrdObservationDate extends AbstractPrdProductDate {

	public PrdObservationDate() {
		super();
	}

	public PrdObservationDate(Date date) {
		super(date);
	}

}
