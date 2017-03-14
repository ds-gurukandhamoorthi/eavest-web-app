/**
 * 
 */
package com.synovia.digital.dto;

import java.net.MalformedURLException;
import java.net.URL;

import javax.validation.constraints.Size;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
public class EavParamsDto {

	private Integer id;
	@Size(max = 4)
	private String numberOfTheMonth;
	private String textOfTheMonth;
	private Boolean isActive;
	@org.hibernate.validator.constraints.URL
	private String leftArticle;
	@org.hibernate.validator.constraints.URL
	private String rightArticle;

	/**
	 * Constructs a default EavNews DTO
	 *
	 */
	public EavParamsDto() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumberOfTheMonth() {
		return this.numberOfTheMonth;
	}

	public void setNumberOfTheMonth(String numberOfTheMonth) {
		this.numberOfTheMonth = numberOfTheMonth;
	}

	public String getTextOfTheMonth() {
		return this.textOfTheMonth;
	}

	public void setTextOfTheMonth(String textOfTheMonth) {
		this.textOfTheMonth = textOfTheMonth;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLeftArticle() {
		return this.leftArticle;
	}

	public URL getLeftArticleAsURL() throws MalformedURLException {
		return new URL(this.leftArticle);
	}

	public void setLeftArticle(String leftArticle) {
		this.leftArticle = leftArticle;
	}

	public String getRightArticle() {
		return this.rightArticle;
	}

	public URL getRightArticleAsURL() throws MalformedURLException {
		return new URL(this.rightArticle);
	}

	public void setRightArticle(String rightArticle) {
		this.rightArticle = rightArticle;
	}

}
