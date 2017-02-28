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
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdCouponDateRepository;
import com.synovia.digital.service.utils.PrdProductDateUtils;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
@Service
public class PrdCouponDateServiceImpl implements PrdCouponDateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdCouponDateServiceImpl.class);
	private final PrdCouponDateRepository repo;
	private final PrdProductService productService;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdCouponDateServiceImpl(PrdCouponDateRepository repo, PrdProductService service) {
		this.repo = repo;
		this.productService = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdCouponService#add(com.synovia.digital.dto.
	 * PrdProductDateDto)
	 */
	@Override
	public PrdCouponDate add(PrdProductDateDto dto) throws EavEntryNotFoundException {
		PrdCouponDate toAdd = (PrdCouponDate) PrdProductDateUtils.convertToEntity(new PrdCouponDate(), dto,
				productService);

		return (!PrdProductDateUtils.isValid(toAdd)) ? null : repo.save(toAdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdCouponService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		repo.delete(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdCouponService#findById(java.lang.Long)
	 */
	@Override
	public PrdCouponDate findById(Long id) {
		return repo.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdCouponService#update(java.lang.Long,
	 * com.synovia.digital.dto.PrdProductDateDto)
	 */
	@Override
	public PrdCouponDate update(Long id, PrdProductDateDto dto) throws EavEntryNotFoundException {
		if (dto == null)
			return null;

		PrdCouponDate entity = repo.findOne(id);
		if (entity == null)
			throw new EavEntryNotFoundException(PrdCouponDate.class.getTypeName());

		PrdProductDateUtils.updateFromDto(entity, dto, productService);
		return repo.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.PrdCouponService#findByIdPrdProduct(java.lang.Long)
	 */
	@Override
	public List<PrdCouponDate> findByIdPrdProduct(Long idPrdProduct) {
		PrdProduct product = null;
		try {
			product = productService.findById(idPrdProduct);
		} catch (EavEntryNotFoundException e) {
			LOGGER.debug(new StringBuilder("No observation dates found for PrdProduct [").append(idPrdProduct)
					.append("]").toString());
		}
		List<PrdCouponDate> found = new ArrayList<>();
		if (product != null) {
			found = repo.findByPrdProduct(product);
		}

		return found;
	}

}
