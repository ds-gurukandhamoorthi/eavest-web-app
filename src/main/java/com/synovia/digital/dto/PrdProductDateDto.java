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
public class PrdProductDateDto {

	private Long id;
	@NotNull
	private String date;
	private Long idPrdProduct;

	public PrdProductDateDto() {
	}

	public PrdProductDateDto(Long id, String date, Long idPrdProduct) {
		this.id = id;
		this.date = date;
		this.idPrdProduct = idPrdProduct;

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getIdPrdProduct() {
		return this.idPrdProduct;
	}

	public void setIdPrdProduct(Long idPrdProduct) {
		this.idPrdProduct = idPrdProduct;
	}

	@Override
	public String toString() {
		return new StringBuilder("[").append("id:").append(this.id.toString()).append("; ").append("date:")
				.append(this.date).append("; ").append("idPrdProduct:").append(this.idPrdProduct).append("]")
				.toString();
	}
}
