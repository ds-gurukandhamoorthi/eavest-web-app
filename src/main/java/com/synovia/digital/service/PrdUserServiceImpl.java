/**
 * 
 */
package com.synovia.digital.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.repository.PrdUserRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
@Service
public class PrdUserServiceImpl implements PrdUserService {

	protected final PrdUserRepository repo;

	/**
	 * Constructs a service to handle interactions with PrdUser model from the database.
	 * 
	 * @param userRepo
	 *            The database relationship.
	 *
	 */
	@Autowired
	public PrdUserServiceImpl(PrdUserRepository userRepo) {
		this.repo = userRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdUserService#getPrdUser(com.synovia.digital.model.
	 * EavAccount)
	 */
	@Override
	public PrdUser getPrdUser(EavAccount account) {
		PrdUser user = null;
		if (account != null) {
			user = repo.findByAccount(account);
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdUserService#findById(java.lang.Long)
	 */
	@Override
	public PrdUser findById(Long id) throws EavEntryNotFoundException {
		PrdUser user = null;
		if (id != null) {
			user = repo.findOne(id);
		}

		if (user == null)
			throw new EavEntryNotFoundException(PrdUser.class.getTypeName());

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdUserService#addProduct(java.lang.Long,
	 * com.synovia.digital.model.PrdProduct)
	 */
	@Override
	public PrdUser addProduct(Long idUser, PrdProduct product) throws EavEntryNotFoundException {
		// Find the user entity
		PrdUser user = findById(idUser);
		// Add the product
		user.getProducts().add(product);
		// Update the entity
		return repo.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdUserService#updateProductList(java.lang.Long,
	 * java.util.Set)
	 */
	@Override
	public PrdUser updateProductList(Long id, Set<PrdProduct> products) throws EavEntryNotFoundException {
		// Find the user entity
		PrdUser user = findById(id);
		// Update the product list
		return updateProductList(user, products);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdUserService#updateProductList(com.synovia.digital.
	 * model.PrdUser, java.util.Set)
	 */
	@Override
	public PrdUser updateProductList(PrdUser user, Set<PrdProduct> products) {
		// Set the product list
		user.setProducts(products);
		// Update the entity
		return repo.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdUserService#removeProduct(java.lang.Long,
	 * com.synovia.digital.model.PrdProduct)
	 */
	@Override
	public PrdUser removeProduct(Long id, PrdProduct product) throws EavEntryNotFoundException {
		// Find the entity
		PrdUser user = repo.findOne(id);
		if (user == null)
			throw new EavEntryNotFoundException(PrdUser.class.getTypeName());
		// Get the list of products
		return removeProduct(user, product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdUserService#removeProduct(com.synovia.digital.model.
	 * PrdUser, com.synovia.digital.model.PrdProduct)
	 */
	@Override
	public PrdUser removeProduct(PrdUser user, PrdProduct product) {
		// Get the list of products and remove
		user.getProducts().remove(product);
		// Update the entity
		return repo.save(user);
	}

}
