/**
 * 
 */
package com.synovia.digital.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdObservationDateRepository;
import com.synovia.digital.service.utils.PrdProductDateUtils;
import com.synovia.digital.service.utils.UpcomingObservationDateComparator;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 25 févr. 2017
 */
@Service
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdObservationDateService#add(com.synovia.digital.dto.
	 * PrdProductDto)
	 */
	@Override
	public PrdObservationDate add(PrdProductDateDto dto) throws EavEntryNotFoundException {
		LOGGER.debug("Call add observation date for DTO {} ", dto.toString());
		PrdObservationDate toAdd = (PrdObservationDate) PrdProductDateUtils.convertToEntity(new PrdObservationDate(),
				dto, productService);
		LOGGER.debug("Convert DTO into entity {} ", toAdd.toString());

		PrdObservationDate result = (!PrdProductDateUtils.isValid(toAdd)) ? null : repo.save(toAdd);
		LOGGER.debug("Save entity {} ", result != null ? result.toString() : null);

		return result;
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
		PrdObservationDate entity = repo.findOne(id);
		if (entity == null)
			throw new EavEntryNotFoundException(PrdObservationDate.class.getTypeName());

		PrdProductDateUtils.updateFromDto(entity, dto, productService);

		return (!PrdProductDateUtils.isValid(entity)) ? null : repo.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdObservationDateService#findByIdPrdProduct(java.lang.
	 * Long)
	 */
	@Override
	public List<PrdObservationDate> findByIdPrdProduct(Long idPrdProduct) {
		PrdProduct product = null;
		try {
			product = productService.findById(idPrdProduct);
		} catch (EavEntryNotFoundException e) {
			LOGGER.debug("No observation dates found for PrdProduct {}", idPrdProduct);
		}
		List<PrdObservationDate> found = new ArrayList<>();
		if (product != null) {
			found = repo.findByPrdProduct(product);
		}

		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdObservationDateService#filterByDate(java.util.Date,
	 * java.util.Date)
	 */
	@Override
	public List<PrdObservationDate> filterByDate(Date from, Date until) {
		List<PrdObservationDate> list = (from == null || until == null) ? new ArrayList<>()
				: repo.findByDateBetween(from, until);
		Collections.sort(list, new UpcomingObservationDateComparator());
		return list;
	}
}
