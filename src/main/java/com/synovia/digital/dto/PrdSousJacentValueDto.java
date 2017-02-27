/**
 * 
 */
package com.synovia.digital.dto;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.synovia.digital.dto.utils.DtoDateFormat;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public class PrdSousJacentValueDto {
	private Long id;

	/** The id of the underlying asset that contains this value. */
	@NotNull
	private Long idPrdSousJacent;

	/** The date of the value [yyyy-MM-dd] */
	@NotNull
	private String date;

	/** The value [EUR] */
	@NotNull
	private Double value;

	public PrdSousJacentValueDto() {

	}

	public Long getIdPrdSousJacent() {
		return this.idPrdSousJacent;
	}

	public void setIdPrdSousJacent(Long idPrdSousJacent) {
		this.idPrdSousJacent = idPrdSousJacent;
	}

	public String getDate() {
		return this.date;
	}

	public Date getDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(date);
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
