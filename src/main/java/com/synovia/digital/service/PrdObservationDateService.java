/**
 * 
 */
package com.synovia.digital.service;

import java.util.Date;
import java.util.List;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdObservationDate;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 25 févr. 2017
 */
public interface PrdObservationDateService {

	public PrdObservationDate add(PrdProductDateDto dto) throws EavEntryNotFoundException;

	public void delete(Long id);

	public void delete(PrdObservationDate date);

	public PrdObservationDate findById(Long id);

	public PrdObservationDate update(Long id, PrdProductDateDto dto) throws EavEntryNotFoundException;

	public List<PrdObservationDate> findByIdPrdProduct(Long idPrdProduct);

	public List<PrdObservationDate> filterByDate(Date from, Date until);
}
