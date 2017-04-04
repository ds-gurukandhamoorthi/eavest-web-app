/**
 * 
 */
package com.synovia.digital.service;

import java.util.List;

import com.synovia.digital.dto.EavAccountDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 févr. 2017
 */
public interface EavAccountService {

	public EavAccount findByEmail(String username);

	public List<EavAccount> findAll();

	public void update(EavAccount entity, EavAccountDto dto);

	public EavAccount findById(Long id) throws EavEntryNotFoundException;

	public EavAccount activate(Long id, Boolean value) throws EavEntryNotFoundException;
}
