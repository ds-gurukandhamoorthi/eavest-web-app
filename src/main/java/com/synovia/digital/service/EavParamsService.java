/**
 * 
 */
package com.synovia.digital.service;

import com.synovia.digital.dto.EavParamsDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.model.EavParams;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
public interface EavParamsService {

	public EavParams addEavParams(EavParamsDto dto);

	public EavParams updateParams(Integer idEavParams, EavParamsDto dto) throws EavEntryNotFoundException;

	public EavParams updateParams(EavParamsDto dto) throws EavEntryNotFoundException;

	public EavParams getEavParams() throws EavTechnicalException;
}
