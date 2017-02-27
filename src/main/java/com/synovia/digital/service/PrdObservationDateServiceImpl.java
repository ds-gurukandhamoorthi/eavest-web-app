/**
 * 
 */
package com.synovia.digital.service;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.repository.PrdObservationDateRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 25 févr. 2017
 */
public class PrdObservationDateServiceImpl implements PrdObservationDateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdObservationDateServiceImpl.class);
	private final PrdObservationDateRepository repo;
	private final PrdProductService productService;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdObservationDateServiceImpl(PrdObservationDateRepository repo, PrdProductService service) {
		this.repo = repo;
		this.productService = service;
	}

	private PrdObservationDate convertToEntity(PrdProductDateDto dto) throws EavEntryNotFoundException {
		PrdObservationDate obsDate = new PrdObservationDate();
		obsDate.setId(dto.getId() != null ? dto.getId() : null);
		try {
			obsDate.setDate(dto.getDate() != null ? dto.getDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [date]");
		}
		obsDate.setPrdProduct(dto.getIdPrdProduct() != null ? productService.findById(dto.getIdPrdProduct()) : null);
		return obsDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdObservationDateService#add(com.synovia.digital.dto.
	 * PrdProductDto)
	 */
	@Override
	public PrdObservationDate add(PrdProductDateDto dto) throws EavEntryNotFoundException {
		if (dto == null)
			return null;

		PrdObservationDate toAdd = convertToEntity(dto);

		return repo.save(toAdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdObservationDateService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		repo.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdObservationDateService#findById(java.lang.Long)
	 */
	@Override
	public PrdObservationDate findById(Long id) {
		return repo.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdObservationDateService#update(java.lang.Long,
	 * com.synovia.digital.dto.PrdProductDto)
	 */
	@Override
	public PrdObservationDate update(Long id, PrdProductDateDto dto) throws EavEntryNotFoundException {
		if (dto == null)
			return null;

		PrdObservationDate entity = repo.findOne(id);
		if (entity == null)
			throw new EavEntryNotFoundException(PrdObservationDate.class.getTypeName());

		updateFromDto(entity, dto);
		return repo.save(entity);
	}

	private void updateFromDto(PrdObservationDate entity, PrdProductDateDto dto) throws EavEntryNotFoundException {
		if (dto.getDate() != null) {
			try {
				entity.setDate(dto.getDateObject());
			} catch (ParseException e) {
				LOGGER.debug("Invalid date format for argument [date]");
			}
		}

		if (dto.getIdPrdProduct() != null) {
			entity.setPrdProduct(productService.findById(dto.getIdPrdProduct()));
		}
	}
}
