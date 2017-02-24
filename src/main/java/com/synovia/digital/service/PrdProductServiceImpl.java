/**
 * 
 */
package com.synovia.digital.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.utils.DtoUtils;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdRule;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;
import com.synovia.digital.utils.PrdStatusEnum;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 17 févr. 2017
 */
@Service
public class PrdProductServiceImpl implements PrdProductService {

	protected PrdProductRepository repo;

	protected PrdSousJacentRepository sousJacentRepo;

	protected PrdStatusRepository statusRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(PrdProductServiceImpl.class);

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	@Autowired
	public PrdProductServiceImpl(PrdProductRepository repo, PrdSousJacentRepository ssjctRepo,
			PrdStatusRepository statusRepo) {
		this.repo = repo;
		this.sousJacentRepo = ssjctRepo;
		this.statusRepo = statusRepo;
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
	public PrdProduct add(PrdProductDto dto) throws EavDuplicateEntryException {
		LOGGER.debug("Adding a new PrdProduct entry with information: {}", dto);
		if (dto == null)
			return null;

		// Search for an existing entry with the same label.
		PrdProduct duplicate = repo.findByIsin(dto.getIsin());
		if (duplicate != null)
			throw new EavDuplicateEntryException(PrdProduct.class.getTypeName());

		// Create the PrdProduct object.
		PrdProduct toAdd = convertToEntity(dto);

		// Save the object to add.
		return repo.save(toAdd);
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
			try {
				entity.setDueDate(dto.getDueDateObject());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (dto.getIsEavest() != null) {
			entity.setIsEavest(dto.getIsEavest());
		}
		if (dto.getEndDate() != null) {
			try {
				entity.setEndDate(dto.getEndDateObject());
			} catch (ParseException e) {
			}
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
			try {
				entity.setLaunchDate(dto.getLaunchDateObject());
			} catch (ParseException e) {
			}
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
			try {
				entity.setSubscriptionEndDate(dto.getSubscriptionEndDateObject());
			} catch (ParseException e) {
			}
		}
		if (dto.getSubscriptionStartDate() != null) {
			try {
				entity.setSubscriptionStartDate(dto.getSubscriptionStartDateObject());
			} catch (ParseException e) {
			}
		}
		if (dto.getObservationFrequency() != null) {
			entity.setObservationFrequency(dto.getObservationFrequency());
		}
		if (dto.getCouponBarrier() != null) {
			entity.getPrdRule().setCouponBarrier(dto.getCouponBarrier());
		}
		if (dto.getProtectionBarrier() != null) {
			entity.getPrdRule().setProtectionBarrier(dto.getProtectionBarrier());
		}
		if (dto.getIdPrdSousJacent() != null) {
			entity.setPrdSousJacent(sousJacentRepo.findOne(dto.getIdPrdSousJacent()));
		}
		if (dto.getStrike() != null) {
			entity.setStrike(dto.getStrike());
		}
	}

	private PrdProduct convertToEntity(PrdProductDto dto) {
		PrdProduct entity = new PrdProduct();

		entity.setId(dto.getId() != null ? dto.getId() : null);
		entity.setIsin(dto.getIsin() != null ? dto.getIsin() : null);
		entity.setLabel(dto.getLabel() != null ? dto.getLabel() : null);
		try {
			entity.setLaunchDate(dto.getLaunchDate() != null ? dto.getLaunchDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [launchDate]");
		}
		try {
			entity.setDueDate(dto.getDueDate() != null ? dto.getDueDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [dueDate]");
		}
		entity.setPrdSousJacent(
				dto.getIdPrdSousJacent() != null ? sousJacentRepo.findOne(dto.getIdPrdSousJacent()) : null);
		entity.setPrdRule(new PrdRule(dto.getProtectionBarrier(), dto.getCouponBarrier()));
		if (dto.getObservationDates() != null) {
			Set<PrdObservationDate> obsDates = new HashSet<>();
			try {
				for (Date d : DtoUtils.convertAsList(dto.getObservationDates())) {
					obsDates.add(new PrdObservationDate(d));
				}
			} catch (ParseException e) {
				obsDates = null;
				LOGGER.debug("Invalid date format for argument [observationDates]");
			}
			entity.setObservationDates(obsDates);
		}
		if (dto.getEarlyRepaymentDates() != null) {
			Set<PrdEarlierRepaymentDate> dates = new HashSet<>();
			try {
				for (Date d : DtoUtils.convertAsList(dto.getEarlyRepaymentDates())) {
					dates.add(new PrdEarlierRepaymentDate(d));
				}
			} catch (ParseException e) {
				dates = null;
				LOGGER.debug("Invalid date format for argument [earlyRepaymentDates]");
			}
			entity.setEarlyRepaymentDates(dates);
		}
		if (dto.getCouponPaymentDates() != null) {
			Set<PrdCouponDate> dates = new HashSet<>();
			try {
				for (Date d : DtoUtils.convertAsList(dto.getCouponPaymentDates())) {
					dates.add(new PrdCouponDate(d));
				}
			} catch (ParseException e) {
				dates = null;
				LOGGER.debug("Invalid date format for argument [couponPaymentDates]");
			}
			entity.setCouponPaymentDates(dates);
		}
		try {
			entity.setSubscriptionStartDate(
					dto.getSubscriptionStartDate() != null ? dto.getSubscriptionStartDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [subscriptionStartDate]");
		}
		try {
			entity.setSubscriptionEndDate(
					dto.getSubscriptionEndDate() != null ? dto.getSubscriptionEndDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [subscriptionEndDate]");
		}
		entity.setCouponValue(dto.getCouponValue() != null ? dto.getCouponValue() : null);
		entity.setNominalValue(dto.getNominalValue() != null ? dto.getNominalValue() : null);
		entity.setCapitalGuaranteed(dto.getCapitalGuaranteed() != null ? dto.getCapitalGuaranteed() : null);
		entity.setStartPrice(dto.getStartPrice() != null ? dto.getStartPrice() : null);
		entity.setDeliver(dto.getDeliver() != null ? dto.getDeliver() : null);
		entity.setGuarantor(dto.getGuarantor() != null ? dto.getGuarantor() : null);
		try {
			entity.setPrdStatus(dto.getStatusCode() != null
					? statusRepo.findByCode(PrdStatusEnum.valueOf(dto.getStatusCode()).toString())
					: statusRepo.findByCode(PrdStatusEnum.IDLE.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			entity.setPrdStatus(statusRepo.findByCode(PrdStatusEnum.IDLE.toString()));
		}
		try {
			entity.setEndDate(dto.getEndDate() != null ? dto.getEndDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [endDate]");
		}
		if (dto.getIsEavest() != null) {
			entity.setIsEavest(dto.getIsEavest());
		}
		if (dto.getIsBestSeller() != null) {
			entity.setIsBestSeller(dto.getIsBestSeller());
		}
		entity.setPath(dto.getPath() != null ? dto.getPath() : null);
		entity.setStrike(dto.getStrike() != null ? dto.getStrike() : null);
		entity.setObservationFrequency(dto.getObservationFrequency() != null ? dto.getObservationFrequency() : null);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.PrdProductService#findAll()
	 */
	@Override
	public Iterable<PrdProduct> findAll() {
		return repo.findAll();
	}
}
