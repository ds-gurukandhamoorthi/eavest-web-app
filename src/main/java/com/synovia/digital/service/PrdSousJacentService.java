/**
 * 
 */
package com.synovia.digital.service;

import java.util.List;
import java.util.Map;

import com.synovia.digital.dto.PrdSousJacentValueDto;
import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.exceptions.EavConstraintViolationEntry;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdSousJacent;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public interface PrdSousJacentService {

	public PrdSousJacent add(PrdSousjacentDto sousJacentDto)
			throws EavDuplicateEntryException, EavConstraintViolationEntry;

	public PrdSousJacent addValue(Long idSousJacent, String date, Double value) throws EavEntryNotFoundException;

	public PrdSousJacent addValue(Long idSousJacent, PrdSousJacentValueDto valueDto) throws EavEntryNotFoundException;

	public PrdSousJacent addValues(Long idSousJacent, Map<String, Double> values) throws EavEntryNotFoundException;

	public PrdSousJacent removeValue(Long idSousJacent, Long idSousJacentValue) throws EavEntryNotFoundException;

	public PrdSousJacent removeValues(Long idSousJacent, List<Long> idSousJacentValues)
			throws EavEntryNotFoundException;

	/**
	 * Updates an underlying asset from a DTO object. The DTO object contains the
	 * identifier of the {@code PrdSousJacent} to update.
	 * 
	 * @param updatedSousJacentDto
	 *            The DTO object.
	 */
	public PrdSousJacent update(PrdSousjacentDto updatedSousJacentDto)
			throws EavEntryNotFoundException, EavConstraintViolationEntry;

	public List<PrdSousJacent> findAll();

	public PrdSousJacent findById(Long id) throws EavEntryNotFoundException;

	public void delete(Long id);

	public List<PrdSousJacent> getClassicBases();

	public List<PrdSousJacent> getNewBases();
}
