/**
 * 
 */
package com.synovia.digital.model;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.context.i18n.LocaleContextHolder;

import com.synovia.digital.utils.EavUtils;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 mars 2017
 */
@Embeddable
public class EavArticle {
	// ------------------------------ FIELDS ------------------------------

	@Column(name = "URL", nullable = false)
	private URL url;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "RELEASE_DATE", nullable = false)
	private Date releaseDate;

	@Column(name = "AUTHOR", nullable = false)
	private String author;

	@Column(name = "CONTENT", nullable = false)
	private String content;

	// --------------------- CONSTRUCTOR(S) ---------------------

	/**
	 * Default Constructor.
	 *
	 */
	public EavArticle() {
		this(null, null, null, null, null);
	}

	public EavArticle(URL url, String title, Date releaseDate, String author, String content) {
		this.url = url;
		this.title = title;
		this.releaseDate = releaseDate;
		this.author = author;
		this.content = content;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public URL getUrl() {
		return this.url;
	}

	public String getUrlAsString() {
		return this.url.toExternalForm();
	}

	public void setUrl(URL url) {
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

	public String getReleaseDateAsLongFormatString() {
		DateFormat formatter = new SimpleDateFormat(EavUtils.LONG_DATE_FORMAT_PATTERN, LocaleContextHolder.getLocale());
		return formatter.format(this.releaseDate);
	}

	public String getReleaseDateAsShortFormatString() {
		DateFormat formatter = new SimpleDateFormat(EavUtils.MID_DATE_FORMAT_PATTERN, LocaleContextHolder.getLocale());
		return formatter.format(this.releaseDate);
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
