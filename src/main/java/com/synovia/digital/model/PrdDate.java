/**
 * 
 */
package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 24 févr. 2017
 */
@Embeddable
public class PrdDate {

	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	private Date date;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public PrdDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
