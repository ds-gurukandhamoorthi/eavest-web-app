/**
 * 
 */
package com.synovia.digital.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdUser;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 26 janv. 2017
 */
@Service
public interface PrdUserService {

	public PrdUser getPrdUser(EavAccount account);

	public PrdUser findById(Long id) throws EavEntryNotFoundException;

	public PrdUser addProduct(Long idUser, PrdProduct product) throws EavEntryNotFoundException;

	public PrdUser updateProductList(Long id, Set<PrdProduct> products) throws EavEntryNotFoundException;

	public PrdUser updateProductList(PrdUser user, Set<PrdProduct> products);

	public PrdUser removeProduct(Long id, PrdProduct product) throws EavEntryNotFoundException;

	public PrdUser removeProduct(PrdUser user, PrdProduct product);
}
