/**
 * 
 */
package com.synovia.digital.service.utils;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.AbstractPrdProductDate;
import com.synovia.digital.service.PrdProductService;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
public class PrdProductDateUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(PrdProductDateUtils.class);

	/**
	 * Converts a DTO into an entity. The DTO represents any of an observation date, a
	 * refund date, or a coupon payment date.
	 * 
	 * @param entity
	 *            The entity. May be empty.
	 * @param dto
	 *            The data transfer object to convert.
	 * @param service
	 *            The service that handles the PrdProduct model.
	 * @throws EavEntryNotFoundException
	 */
	public static AbstractPrdProductDate convertToEntity(AbstractPrdProductDate entity, PrdProductDateDto dto,
			final PrdProductService service) throws EavEntryNotFoundException {
		if (entity == null || dto == null)
			return null;

		entity.setId(dto.getId() != null ? dto.getId() : null);
		try {
			entity.setDate(dto.getDate() != null ? dto.getDateObject() : null);
		} catch (ParseException e) {
			LOGGER.debug("Invalid date format for argument [date]");
		}
		entity.setPrdProduct(dto.getIdPrdProduct() != null ? service.findById(dto.getIdPrdProduct()) : null);

		return entity;
	}

	public static void updateFromDto(AbstractPrdProductDate entity, PrdProductDateDto dto,
			final PrdProductService service) throws EavEntryNotFoundException {
		if (entity == null || dto == null)
			return;

		if (dto.getDate() != null) {
			try {
				entity.setDate(dto.getDateObject());
			} catch (ParseException e) {
				LOGGER.debug("Invalid date format for argument [date]");
			}
		}

		if (dto.getIdPrdProduct() != null) {
			entity.setPrdProduct(service.findById(dto.getIdPrdProduct()));
		}
	}

	public static boolean isValid(AbstractPrdProductDate entity) {
		return (entity != null && entity.getDate() != null && entity.getPrdProduct() != null);
	}
}
