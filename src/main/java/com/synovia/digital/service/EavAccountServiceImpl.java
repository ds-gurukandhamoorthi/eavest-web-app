/**
 * 
 */
package com.synovia.digital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.EavAccountDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavAccountService#findAll()
	 */
	@Override
	public List<EavAccount> findAll() {
		return repo.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.EavAccountService#update(com.synovia.digital.model.
	 * EavAccount, com.synovia.digital.dto.EavAccountDto)
	 */
	@Override
	public void update(EavAccount entity, EavAccountDto dto) {
		updateFromDto(entity, dto);

		// Save the entity
		repo.save(entity);
	}

	private void updateFromDto(EavAccount entity, EavAccountDto dto) {
		if (dto.getNewsletter() != null) {
			entity.setNewsletter(dto.getNewsletter());
		}

		if (dto.getAliasName() != null) {
			entity.setAliasName(dto.getAliasName());
		}

		if (dto.getCompany() != null) {
			entity.setCompany(dto.getCompany());
		}

		if (dto.getCountry() != null) {
			entity.setCountry(dto.getCountry());
		}

		if (dto.getFirstName() != null) {
			entity.setFirstName(dto.getFirstName());
		}

		if (dto.getFixPhone() != null) {
			entity.setFixPhone(dto.getFixPhone());
		}

		if (dto.getLastName() != null) {
			entity.setLastName(dto.getLastName());
		}

		if (dto.getMobilePhone() != null) {
			entity.setMobilePhone(dto.getMobilePhone());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavAccountService#findById(java.lang.Long)
	 */
	@Override
	public EavAccount findById(Long id) throws EavEntryNotFoundException {
		EavAccount found = repo.findOne(id);
		if (found == null)
			throw new EavEntryNotFoundException(EavAccount.class.getTypeName());

		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavAccountService#activate(java.lang.Long,
	 * java.lang.Boolean)
	 */
	@Override
	public EavAccount activate(Long id, Boolean value) throws EavEntryNotFoundException {
		EavAccount account = findById(id);
		if (value != null) {
			account.setEnabled(value);
			account = repo.save(account);
		}
		return account;
	}

}
