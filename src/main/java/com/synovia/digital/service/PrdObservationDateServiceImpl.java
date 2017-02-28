/**
 * 
 */
package com.synovia.digital.service;

import java.util.ArrayList;
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
		PrdObservationDate toAdd = (PrdObservationDate) PrdProductDateUtils.convertToEntity(new PrdObservationDate(),
				dto, productService);

		return (!PrdProductDateUtils.isValid(toAdd)) ? null : repo.save(toAdd);
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
			LOGGER.debug(new StringBuilder("No observation dates found for PrdProduct [").append(idPrdProduct)
					.append("]").toString());
		}
		List<PrdObservationDate> found = new ArrayList<>();
		if (product != null) {
			found = repo.findByPrdProduct(product);
		}

		return found;
	}
}
