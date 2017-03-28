/**
 * 
 */
package com.synovia.digital.service;

import java.util.Date;

import com.synovia.digital.dto.PrdSousJacentValueDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdSousJacentValue;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 15 févr. 2017
 */
public interface PrdSousJacentValueService {

	public PrdSousJacentValue create(PrdSousJacentValueDto valueDto);

	public void delete(PrdSousJacentValue model);

	public void delete(Long id);

	public PrdSousJacentValue findById(Long id) throws EavEntryNotFoundException;

	public PrdSousJacentValue getValue(PrdSousJacent ssjct, Date date) throws EavTechnicalException;
}
