/**
 * 
 */
package com.synovia.digital.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

	@Column(name = "NUMBER_OF_MONTH", length = 6)
	private String numberOfTheMonth;

	@Column(name = "TEXT_OF_MONTH", length = 160)
	private String textOfTheMonth;

	@Column(name = "IS_ACTIVE")
	private Boolean isActive = false;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "url", column = @Column(name = "LEFT_ARTICLE_URL")),
			@AttributeOverride(name = "title", column = @Column(name = "LEFT_ARTICLE_TITLE", length = 100)),
			@AttributeOverride(name = "releaseDate", column = @Column(name = "LEFT_ARTICLE_RELEASE_DATE")),
			@AttributeOverride(name = "author", column = @Column(name = "LEFT_ARTICLE_AUTHOR")),
			@AttributeOverride(name = "content", column = @Column(name = "LEFT_ARTICLE_CONTENT", length = 240)) })
	private EavArticle leftArticle;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "url", column = @Column(name = "RIGHT_ARTICLE_URL")),
			@AttributeOverride(name = "title", column = @Column(name = "RIGHT_ARTICLE_TITLE", length = 100)),
			@AttributeOverride(name = "releaseDate", column = @Column(name = "RIGHT_ARTICLE_RELEASE_DATE")),
			@AttributeOverride(name = "author", column = @Column(name = "RIGHT_ARTICLE_AUTHOR")),
			@AttributeOverride(name = "content", column = @Column(name = "RIGHT_ARTICLE_CONTENT", length = 160)) })
	private EavArticle rightArticle;

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

	public EavArticle getLeftArticle() {
		return this.leftArticle;
	}

	public void setLeftArticle(EavArticle leftArticle) {
		this.leftArticle = leftArticle;
	}

	public EavArticle getRightArticle() {
		return this.rightArticle;
	}

	public void setRightArticle(EavArticle rightArticle) {
		this.rightArticle = rightArticle;
	}

}
