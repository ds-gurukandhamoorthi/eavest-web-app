/**
 * 
 */
package com.synovia.digital.service;

import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synovia.digital.dto.EavParamsDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.exceptions.utils.EavErrorCode;
import com.synovia.digital.model.EavParams;
import com.synovia.digital.repository.EavParamsRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
@Service
public class EavParamsServiceImpl implements EavParamsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EavParamsServiceImpl.class);

	protected final EavParamsRepository repo;

	/**
	 * Constructs a service for EavNews entity.
	 *
	 */
	@Autowired
	public EavParamsServiceImpl(EavParamsRepository newsRepo) {
		this.repo = newsRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavNewsService#addEavNews(com.synovia.digital.dto.
	 * EavNewsDto)
	 */
	@Override
	public EavParams addEavParams(EavParamsDto dto) {
		LOGGER.debug("Adding a new EavNews entry from DTO: {}", dto);
		if (dto == null)
			return null;

		// Create the EavNews entity
		EavParams eavParams = convertToEntity(dto);

		// Save the entity
		return repo.save(eavParams);
	}

	private EavParams convertToEntity(EavParamsDto dto) {
		EavParams eavParams = new EavParams();
		eavParams.setId(dto.getId());
		return eavParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavParamsService#updateParams(java.lang.Integer,
	 * com.synovia.digital.dto.EavParamsDto)
	 */
	@Override
	public EavParams updateParams(Integer idEavParams, EavParamsDto dto) throws EavEntryNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.synovia.digital.service.EavParamsService#updateParams(com.synovia.digital.dto.
	 * EavParamsDto)
	 */
	@Override
	public EavParams updateParams(EavParamsDto dto) throws EavEntryNotFoundException {
		EavParams toUpdate = null;
		if (dto == null) {
			LOGGER.warn("Try to update an entity with a null DTO entry.");
			return toUpdate;
		}

		if (dto.getId() != null) {
			// Find the corresponding entity by the DTO id
			LOGGER.info("Update EavParams table from DTO ID: {}.", dto.getId());
			toUpdate = repo.findOne(dto.getId());

		} else {
			// Get the only one existing entity
			// That case might always be true. We assume that there should be only one entry in this table
			LOGGER.info("Update EavParams table for the only one existing entry.");
			Iterable<EavParams> eavParamsVersions = repo.findAll();
			int length = 0;
			for (EavParams p : eavParamsVersions) {
				toUpdate = p;
				if (length > 0) {
					toUpdate = null;
				}
				length++;
			}
		}

		if (toUpdate != null) {
			updateFromDto(toUpdate, dto);
		} else
			throw new EavEntryNotFoundException(EavParams.class.getTypeName());

		// Save the entity
		return repo.save(toUpdate);

	}

	/**
	 * 
	 * @param entity
	 * @param dto
	 */
	private void updateFromDto(EavParams entity, EavParamsDto dto) {
		if (dto.getIsActive() != null) {
			entity.setIsActive(dto.getIsActive());
			if (dto.getIsActive() == true) {
				// Sets the current active entity(ies) to deactivated
				List<EavParams> activeParams = repo.findByIsActive(true);
				for (EavParams p : activeParams) {
					p.setIsActive(false);
					repo.save(p);
				}
			}
		}

		if (dto.getNumberOfTheMonth() != null) {
			entity.setNumberOfTheMonth(dto.getNumberOfTheMonth());
		}

		if (dto.getTextOfTheMonth() != null) {
			entity.setTextOfTheMonth(dto.getTextOfTheMonth());
		}

		if (dto.getLeftArticle() != null) {
			try {
				entity.setLeftArticle(dto.getLeftArticleAsURL());
			} catch (MalformedURLException e) {
				LOGGER.warn("Invalid URL {} for the left article", dto.getLeftArticle());
			}
		}

		if (dto.getRightArticle() != null) {
			try {
				entity.setRightArticle(dto.getRightArticleAsURL());
			} catch (MalformedURLException e) {
				LOGGER.warn("Invalid URL {} for the right article", dto.getRightArticle());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.synovia.digital.service.EavParamsService#getEavParams()
	 */
	@Override
	public EavParams getEavParams() throws EavTechnicalException {
		List<EavParams> eavParamsList = repo.findByIsActive(true);
		if (eavParamsList.size() != 1)
			throw new EavTechnicalException(EavErrorCode.MULTIPLE_EAV_PARAMS);

		return eavParamsList.get(0);
	}
}
