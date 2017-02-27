/**
 * 
 */
package com.synovia.digital.service;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.PrdSousJacentValueDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdSousJacentValue;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdSousJacentValueRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 15 févr. 2017
 */
@Service
public class PrdSousJacentValueServiceImpl implements PrdSousJacentValueService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdSousJacentValueServiceImpl.class);

	protected final PrdSousJacentValueRepository repo;

	protected PrdSousJacentRepository sousJacentRepo;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdSousJacentValueServiceImpl(PrdSousJacentValueRepository repo, PrdSousJacentRepository ssjctRepo) {
		this.repo = repo;
		this.sousJacentRepo = ssjctRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdSousJacentValueService#create(com.synovia.digital.
	 * model.PrdSousJacent, com.synovia.digital.dto.PrdSousJacentValueDto)
	 */
	@Override
	public PrdSousJacentValue create(PrdSousJacentValueDto valueDto) {
		if (valueDto == null)
			return null;

		// Create the entity.
		PrdSousJacentValue entity = convertToEntity(valueDto);

		if (!(entity.getDate() != null && entity.getValue() != null)) {
			LOGGER.error(
					new StringBuilder("Indexed key error for input value [").append(valueDto).append("]").toString());
		}

		return repo.save(entity);
	}

	private PrdSousJacentValue convertToEntity(PrdSousJacentValueDto dto) {
		PrdSousJacentValue entity = new PrdSousJacentValue();

		entity.setId(dto.getId() != null ? dto.getId() : null);
		try {
			entity.setDate(dto.getDate() != null ? dto.getDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [date]");
		}
		entity.setValue(dto.getValue() != null ? dto.getValue() : null);
		entity.setPrdSousJacent(dto.getIdPrdSousJacent() != null ? sousJacentRepo.findOne(dto.getId()) : null);
		return entity;

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
