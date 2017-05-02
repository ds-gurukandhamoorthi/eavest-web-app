package com.synovia.digital.utils;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.synovia.digital.exceptions.EavTechnicalException;


/**
 * This class helps extract (web-scrap) stock and other financial instruments' price from 
 * known financial websites
 * 
 * @author Swaminathan Gurukandhamoorthi
 * @since 24/04/2017
 */
public class WebScrapingUtils {

	private static final String ABCBOURSE_PATTERN = "abcbourse";
	private static final String STOXX_PATTERN = "stoxx";
	private static final String BOURSORAMA_PATTERN = "boursorama";
	private static final String BLOOMBERG_PATTERN = "bloomberg";

	public static final String XWEBSCRAP_INVALID_URL = "webscrap.invalid.url";
	public static final String XWEBSCRAP_PAGE_PARSING_PROBLEM = "webscrap.page.parsing.problem";
	public static final String XWEBSCRAP_UNFAMILIAR_URL = "webscrap.unfamiliar.url";
	public static final String XWEBSCRAP_URL_NULL = "webscrap.url.null";
	public static final String XWEBSCRAP_INVALID_PRICE = "webscrap.invalid.price";
	private enum KNOWN_WEBSITES {
		BOURSORAMA,
		STOXX,
		BLOOMBERG,
		ABCBOURSE
	};

	
	/**
	 * Extract previous day's closing value from a given url of a financial instrument
	 *
	 * @param url the url of the website to retrieve value from
	 * @throws EavTechnicalException
	 */

	public static double getPrevDayClose(String url) throws EavTechnicalException {
		if (url == null) {
			throw new EavTechnicalException(XWEBSCRAP_URL_NULL);
		}
		if (url.toLowerCase().contains(BOURSORAMA_PATTERN)) {
			return getPrevDayCloseFrom(url, KNOWN_WEBSITES.BOURSORAMA);
		} else if (url.toLowerCase().contains(BLOOMBERG_PATTERN)) {
			return getPrevDayCloseFrom(url, KNOWN_WEBSITES.BLOOMBERG);
		} else if (url.toLowerCase().contains(STOXX_PATTERN)) {
			return getPrevDayCloseFrom(url, KNOWN_WEBSITES.STOXX);
		} else if (url.toLowerCase().contains(ABCBOURSE_PATTERN)) {
			return getPrevDayCloseFrom(url, KNOWN_WEBSITES.ABCBOURSE);
		}
		throw new EavTechnicalException(XWEBSCRAP_UNFAMILIAR_URL);
	}

	

	/**
	 * Extract current price from a given url of a financial instrument
	 *
	 * @param url the url of the website to retrieve value from
	 * @throws EavTechnicalException
	 */
	public static double getCurrPriceFrom(String url) throws EavTechnicalException {
		if (url.toLowerCase().contains(BOURSORAMA_PATTERN)) {
			return getCurrPriceFrom(url, KNOWN_WEBSITES.BOURSORAMA);
		} else if (url.toLowerCase().contains(BLOOMBERG_PATTERN)) {
			return getCurrPriceFrom(url, KNOWN_WEBSITES.BLOOMBERG);
		} 
		throw new EavTechnicalException(XWEBSCRAP_UNFAMILIAR_URL);
	}

	

	
	/**
	 * Extract previous day's closing value of a financial instrument from a given url  (website wise)
	 *
	 * @param url the url of the website to retrieve value from
	 * @param sourceWebsite the name of the website to retrieve value from (ex. Boursorama, Bloomberg)
	 * @throws EavTechnicalException
	 */
	private static double getPrevDayCloseFrom(String url, KNOWN_WEBSITES sourceWebsite) throws EavTechnicalException {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new EavTechnicalException(XWEBSCRAP_INVALID_URL, e);
		}

		// There is an alternative way of extracting price using itemscope
		// itemprop of meta
		String price=null;
		try {
			switch (sourceWebsite) {

			case BOURSORAMA:
				price = doc.select("td:contains(Clôture veille)").first().nextElementSibling().text().replaceAll(" ",
						"");
				break;
			case BLOOMBERG:
				price = doc.select("div.cell__label:contains(Previous Close)").first().nextElementSibling().text()
				.replace(",", "");
				break;
			case STOXX:
				price = doc.select("td:contains(Clôture veille)").first().nextElementSibling().text().split(" ")[0]
						.replace(".", "");
				break;
			case ABCBOURSE:
				price = doc.select("td:contains(Clôture veille)").first().nextElementSibling().text().replaceAll(" ", "")
				.replace(",", ".");
				break;
			}

		} catch (NullPointerException e) {
			throw new EavTechnicalException(XWEBSCRAP_PAGE_PARSING_PROBLEM);
		}

		if (price == null || price.isEmpty())
			throw new EavTechnicalException(XWEBSCRAP_INVALID_PRICE);
		return Double.valueOf(price);

	}
	
	/**
	 * Extract current value of a financial instrument from a given url (website wise)
	 *
	 * @param url the url of the website to retrieve value from
	 * @param sourceWebsite the name of the website to retrieve value from (ex. Boursorama, Bloomberg)
	 * @throws EavTechnicalException
	 */
	private static double getCurrPriceFrom(String url, KNOWN_WEBSITES sourceWebsite) throws EavTechnicalException {
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				throw new EavTechnicalException(XWEBSCRAP_INVALID_URL, e);
			}

			// There is an alternative way of extracting price using itemscope
			// itemprop of meta
			String price=null;
			try {
				switch (sourceWebsite) {

				case BOURSORAMA:
					price = doc.select("span.cotation").first().text().split(" ")[0].replace(".", "");;
					break;
				case BLOOMBERG:
					price = doc.select("div.price").first().text().replace(",", "");
					break;
				}

			} catch (NullPointerException e) {
				throw new EavTechnicalException(XWEBSCRAP_PAGE_PARSING_PROBLEM);
			}

			if (price == null || price.isEmpty())
				throw new EavTechnicalException(XWEBSCRAP_INVALID_PRICE);
			return Double.valueOf(price);

		}

}
