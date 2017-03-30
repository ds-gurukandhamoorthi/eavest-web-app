/**
 * 
 */
package com.synovia.digital.service;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdUser;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public interface PrdProductService {

	public PrdProduct findById(Long id) throws EavEntryNotFoundException;

	public List<PrdProduct> findAll();

	public Page<PrdProduct> findAll(int pageIdx, int nbMaxProducts);

	public PrdProduct add(PrdProductDto dto) throws EavTechnicalException;

	public List<PrdProduct> listRefundProducts(Date from);

	public List<PrdProduct> listUpcomingProducts(Date from, Date until);

	public List<PrdProduct> listUserRefundProducts(Date from, PrdUser user);

	public List<PrdProduct> listUserUpcomingProducts(Date from, Date until, PrdUser user);

	/**
	 * Creates an identifier name for a given product.
	 * <p>
	 * The packed name to return is created by appending the company, the ISIN code, the
	 * name and the underlying asset of the given product.
	 * </p>
	 * <p>
	 * For example:<br/>
	 * For a product whose company holder is <strong>Société Générale</strong>, ISIN is
	 * <strong>FR0013210317</strong>, name is <strong>MFM RENDEMENT JAN 2017</strong>, and
	 * underlying asset is <strong>Indice CAC 40</strong>,<br/>
	 * The result returned by this method would be:<br/>
	 * "{@code Société Générale, FR0013210317, MFM RENDEMENT JAN 2017, Indice CAC 40}"
	 * 
	 * @param product
	 *            The product
	 * @return The packed name of the product.
	 */
	public String generatePackedName(PrdProduct product);

	public String generatePackedName(Long id);

	public List<String> getPackedNameList(Collection<PrdProduct> products);

	public PrdProduct setBestSeller(PrdProductDto dto) throws EavEntryNotFoundException;

	public PrdProduct findBestSeller() throws EavTechnicalException;

	public File getImage(PrdProduct product);

	public void storeDocuments(PrdProduct product);

	public File getBestSellerImage();

	public void storeImage(Long id, MultipartFile fileToStore) throws EavTechnicalException;

	public void storeImage(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException;

	public void storeTermSheet(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException;

	public void storeFease(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException;

	public void storeMarketingDoc(PrdProduct product, MultipartFile fileToStore) throws EavTechnicalException;

	public List<PrdProduct> getUserProducts(PrdUser user);

	public File getFease(PrdProduct product);

	public File getTermSheet(PrdProduct product);

	public File getMarketingDoc(PrdProduct product);

	public void delete(Long id) throws EavEntryNotFoundException;

	public void delete(PrdProduct product);

	public void update(PrdProduct product, PrdProductDto dto) throws EavTechnicalException;
}
