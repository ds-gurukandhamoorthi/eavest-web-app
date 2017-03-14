/**
 * 
 */
package com.synovia.digital.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 mars 2017
 */
@Embeddable
public class EavArticle {
	// ------------------------------ FIELDS ------------------------------

	@Column(name = "URL")
	private String url;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "RELEASE_DATE")
	private Date releaseDate;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "CONTENT")
	private String content;

	// --------------------- CONSTRUCTOR(S) ---------------------

	/**
	 * Default Constructor.
	 *
	 */
	public EavArticle() {
		this(null, null, null, null, null);
	}

	public EavArticle(String url, String title, Date releaseDate, String author, String content) {
		this.url = url;
		this.title = title;
		this.releaseDate = releaseDate;
		this.author = author;
		this.content = content;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
