/**
 * 
 */
package com.synovia.digital.model;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
@Entity
@Table(name = "eav_params", schema = "test")
public class EavParams {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;

	@Column(name = "NUMBER_OF_MONTH", length = 4)
	private String numberOfTheMonth;

	@Column(name = "TEXT_OF_MONTH")
	private String textOfTheMonth;

	@Column(name = "IS_ACTIVE")
	private Boolean isActive = false;

	@Column(name = "LEFT_ARTICLE")
	private URL leftArticle;

	@Column(name = "RIGHT_ARTICLE")
	private URL rightArticle;

	// -------------------------- CONSTRUCTORS --------------------------

	/**
	 * Constructs a default EavParams entity.
	 *
	 */
	public EavParams() {
	}

	// -------------------------- GETTERS/SETTERS --------------------------

	public Integer getId() {
		return this.Id;
	}

	public void setId(Integer id) {
		this.Id = id;
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

	public URL getLeftArticle() {
		return this.leftArticle;
	}

	public String getLeftArticleAsString() {
		return (this.leftArticle != null) ? this.leftArticle.toString() : null;
	}

	public void setLeftArticle(URL leftArticle) {
		this.leftArticle = leftArticle;
	}

	public URL getRightArticle() {
		return this.rightArticle;
	}

	public String getRightArticleAsString() {
		return (this.rightArticle != null) ? this.rightArticle.toString() : null;
	}

	public void setRightArticle(URL rightArticle) {
		this.rightArticle = rightArticle;
	}

}
