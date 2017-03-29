/**
 * 
 */
package com.synovia.digital.dto;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.Size;

import com.synovia.digital.dto.utils.DtoDateFormat;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
public class EavParamsDto {

	private Integer id;

	@Size(max = 6)
	private String numberOfTheMonth;

	@Size(max = 255)
	private String textOfTheMonth;

	private Boolean isActive;

	@org.hibernate.validator.constraints.URL
	private String leftArticleUrl;

	@Size(max = 100)
	private String leftArticleTitle;

	private String leftArticleReleaseDate;

	private String leftArticleAuthor;

	@Size(max = 240)
	private String leftArticleContent;

	@org.hibernate.validator.constraints.URL
	private String rightArticleUrl;

	@Size(max = 100)
	private String rightArticleTitle;

	private String rightArticleReleaseDate;

	private String rightArticleAuthor;

	@Size(max = 160)
	private String rightArticleContent;

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

	public String getLeftArticleUrl() {
		return this.leftArticleUrl;
	}

	public URL getLeftArticleUrlObject() throws MalformedURLException {
		return new URL(this.leftArticleUrl);
	}

	public void setLeftArticleUrl(String leftArticleUrl) {
		this.leftArticleUrl = leftArticleUrl;
	}

	public String getRightArticleUrl() {
		return this.rightArticleUrl;
	}

	public String getLeftArticleTitle() {
		return this.leftArticleTitle;
	}

	public void setLeftArticleTitle(String leftArticleTitle) {
		this.leftArticleTitle = leftArticleTitle;
	}

	public String getLeftArticleReleaseDate() {
		return this.leftArticleReleaseDate;
	}

	public Date getLeftArticleReleaseDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.leftArticleReleaseDate);
	}

	public void setLeftArticleReleaseDate(String leftArticleReleaseDate) {
		this.leftArticleReleaseDate = leftArticleReleaseDate;
	}

	public String getLeftArticleAuthor() {
		return this.leftArticleAuthor;
	}

	public void setLeftArticleAuthor(String leftArticleAuthor) {
		this.leftArticleAuthor = leftArticleAuthor;
	}

	public String getLeftArticleContent() {
		return this.leftArticleContent;
	}

	public void setLeftArticleContent(String leftArticleContent) {
		this.leftArticleContent = leftArticleContent;
	}

	public String getRightArticleTitle() {
		return this.rightArticleTitle;
	}

	public void setRightArticleTitle(String rightArticleTitle) {
		this.rightArticleTitle = rightArticleTitle;
	}

	public String getRightArticleReleaseDate() {
		return this.rightArticleReleaseDate;
	}

	public Date getRightArticleReleaseDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.rightArticleReleaseDate);
	}

	public void setRightArticleReleaseDate(String rightArticleReleaseDate) {
		this.rightArticleReleaseDate = rightArticleReleaseDate;
	}

	public String getRightArticleAuthor() {
		return this.rightArticleAuthor;
	}

	public void setRightArticleAuthor(String rightArticleAuthor) {
		this.rightArticleAuthor = rightArticleAuthor;
	}

	public String getRightArticleContent() {
		return this.rightArticleContent;
	}

	public void setRightArticleContent(String rightArticleContent) {
		this.rightArticleContent = rightArticleContent;
	}

	public URL getRightArticleUrlObject() throws MalformedURLException {
		return new URL(this.rightArticleUrl);
	}

	public void setRightArticleUrl(String rightArticleUrl) {
		this.rightArticleUrl = rightArticleUrl;
	}

}
