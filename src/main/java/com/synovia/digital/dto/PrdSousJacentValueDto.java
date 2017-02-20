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
	/** The id of the underlying asset that contains this value. */
	@NotNull
	private Long idPrdSousJacent;

	/** The date of the value [dd/MM/yyyy] */
	@NotNull
	private Date date;

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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(String date) throws ParseException {
		this.date = DtoDateFormat.getFormat().parse(date);
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
