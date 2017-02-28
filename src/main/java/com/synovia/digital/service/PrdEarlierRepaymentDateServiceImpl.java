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
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdEarlierRepaymentDateRepository;
import com.synovia.digital.service.utils.PrdProductDateUtils;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
@Service
public class PrdEarlierRepaymentDateServiceImpl implements PrdEarlierRepaymentDateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdEarlierRepaymentDateServiceImpl.class);
	private final PrdEarlierRepaymentDateRepository repo;
	private final PrdProductService productService;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdEarlierRepaymentDateServiceImpl(PrdEarlierRepaymentDateRepository repo, PrdProductService service) {
		this.repo = repo;
		this.productService = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdEarlierRepaymentDateService#add(com.synovia.digital.
	 * dto.PrdProductDateDto)
	 */
	@Override
	public PrdEarlierRepaymentDate add(PrdProductDateDto dto) throws EavEntryNotFoundException {
		PrdEarlierRepaymentDate toAdd = (PrdEarlierRepaymentDate) PrdProductDateUtils
				.convertToEntity(new PrdEarlierRepaymentDate(), dto, productService);

		return (!PrdProductDateUtils.isValid(toAdd)) ? null : repo.save(toAdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdEarlierRepaymentDateService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		repo.delete(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdEarlierRepaymentDateService#findById(java.lang.Long)
	 */
	@Override
	public PrdEarlierRepaymentDate findById(Long id) {
		return repo.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdEarlierRepaymentDateService#update(java.lang.Long,
	 * com.synovia.digital.dto.PrdProductDateDto)
	 */
	@Override
	public PrdEarlierRepaymentDate update(Long id, PrdProductDateDto dto) throws EavEntryNotFoundException {
		if (dto == null)
			return null;

		PrdEarlierRepaymentDate entity = repo.findOne(id);
		if (entity == null)
			throw new EavEntryNotFoundException(PrdEarlierRepaymentDate.class.getTypeName());

		PrdProductDateUtils.updateFromDto(entity, dto, productService);
		return repo.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdEarlierRepaymentDateService#findByIdPrdProduct(java.
	 * lang.Long)
	 */
	@Override
	public List<PrdEarlierRepaymentDate> findByIdPrdProduct(Long idPrdProduct) {
		PrdProduct product = null;
		try {
			product = productService.findById(idPrdProduct);
		} catch (EavEntryNotFoundException e) {
			LOGGER.debug(new StringBuilder("No observation dates found for PrdProduct [").append(idPrdProduct)
					.append("]").toString());
		}
		List<PrdEarlierRepaymentDate> found = new ArrayList<>();
		if (product != null) {
			found = repo.findByPrdProduct(product);
		}

		return found;
	}

}
