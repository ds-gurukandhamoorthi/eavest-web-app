/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.PrdSousJacentValueDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdSousJacentValue;
import com.synovia.digital.repository.PrdSousJacentValueRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 15 févr. 2017
 */
@Service
public class PrdSousJacentValueServiceImpl implements PrdSousJacentValueService {

	protected final PrdSousJacentValueRepository repo;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdSousJacentValueServiceImpl(PrdSousJacentValueRepository repo) {
		this.repo = repo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdSousJacentValueService#create(com.synovia.digital.
	 * model.PrdSousJacent, com.synovia.digital.dto.PrdSousJacentValueDto)
	 */
	@Override
	public PrdSousJacentValue create(PrdSousJacent sousJacent, PrdSousJacentValueDto valueDto) {
		PrdSousJacentValue sousJacentValue = new PrdSousJacentValue();
		sousJacentValue.setPrdSousJacent(sousJacent);
		sousJacentValue.setDate(valueDto.getDate());
		sousJacentValue.setValue(valueDto.getValue());

		return repo.save(sousJacentValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdSousJacentValueService#delete(com.synovia.digital.
	 * model.PrdSousJacentValue)
	 */
	@Override
	public void delete(PrdSousJacentValue model) {
		repo.delete(model);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdSousJacentValueService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		repo.delete(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdSousJacentValueService#findById(java.lang.Long)
	 */
	@Override
	public PrdSousJacentValue findById(Long id) throws EavEntryNotFoundException {
		PrdSousJacentValue toFind = repo.findOne(id);

		if (toFind == null)
			throw new EavEntryNotFoundException(PrdSousJacentValue.class.getTypeName());

		return toFind;
	}

}
