/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdProductRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 17 févr. 2017
 */
@Service
public class PrdProductServiceImpl implements PrdProductService {

	protected PrdProductRepository repo;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdProductServiceImpl(PrdProductRepository repo) {
		this.repo = repo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#findById(java.lang.Long)
	 */
	@Override
	public PrdProduct findById(Long id) throws EavEntryNotFoundException {
		PrdProduct toFind = repo.findOne(id);
		if (toFind == null)
			throw new EavEntryNotFoundException(PrdProduct.class.getTypeName());

		return toFind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#add(com.synovia.digital.dto.
	 * PrdProductDto)
	 */
	@Override
	public PrdProduct add(PrdProductDto dto) {
		PrdProduct product = new PrdProduct();
		updateFromDto(product, dto);

		return repo.save(product);
	}

	private void updateFromDto(PrdProduct entity, PrdProductDto dto) {
		if (dto.getIsBestSeller() != null) {
			entity.setIsBestSeller(dto.getIsBestSeller());
		}
		if (dto.getCapitalGuaranteed() != null) {
			entity.setCapitalGuaranteed(dto.getCapitalGuaranteed());
		}
		if (dto.getCouponValue() != null) {
			entity.setCouponValue(dto.getCouponValue());
		}
		if (dto.getDeliver() != null) {
			entity.setDeliver(dto.getDeliver());
		}
		if (dto.getDueDate() != null) {
			entity.setDueDate(dto.getDueDate());
		}
		if (dto.getIsEavest() != null) {
			entity.setIsEavest(dto.getIsEavest());
		}
		if (dto.getEndDate() != null) {
			entity.setEndDate(dto.getEndDate());
		}
		if (dto.getGuarantor() != null) {
			entity.setGuarantor(dto.getGuarantor());
		}
		if (dto.getIsin() != null) {
			entity.setIsin(dto.getIsin());
		}
		if (dto.getLabel() != null) {
			entity.setLabel(dto.getLabel());
		}
		if (dto.getLaunchDate() != null) {
			entity.setLaunchDate(dto.getLaunchDate());
		}
		if (dto.getNominalValue() != null) {
			entity.setNominalValue(dto.getNominalValue());
		}
		if (dto.getPath() != null) {
			entity.setPath(dto.getPath());
		}
		if (dto.getStartPrice() != null) {
			entity.setStartPrice(dto.getStartPrice());
		}
		if (dto.getSubscriptionEndDate() != null) {
			entity.setSubscriptionEndDate(dto.getSubscriptionEndDate());
		}
		if (dto.getSubscriptionStartDate() != null) {
			entity.setSubscriptionStartDate(dto.getSubscriptionStartDate());
		}
		if (dto.getPrdSousJacent() != null) {
			entity.setPrdSousJacent(dto.getPrdSousJacent());
		}
	}
}
