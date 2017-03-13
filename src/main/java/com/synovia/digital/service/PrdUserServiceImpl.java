/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.model.EavAccount;
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

}
