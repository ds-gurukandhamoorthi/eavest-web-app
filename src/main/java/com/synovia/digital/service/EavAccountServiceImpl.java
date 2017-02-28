/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.repository.EavAccountRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 févr. 2017
 */
@Service
public class EavAccountServiceImpl implements EavAccountService {

	private final EavAccountRepository repo;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public EavAccountServiceImpl(EavAccountRepository repo) {
		this.repo = repo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavAccountService#findByEmail(java.lang.String)
	 */
	@Override
	public EavAccount findByEmail(String username) {
		// The user name here is the email
		return repo.findByEmail(username);
	}

}
