/**
 * 
 */
package com.synovia.digital.service;

import java.util.List;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdEarlierRepaymentDate;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
public interface PrdEarlierRepaymentDateService {

	public PrdEarlierRepaymentDate add(PrdProductDateDto dto) throws EavEntryNotFoundException;

	public void delete(Long id);

	public PrdEarlierRepaymentDate findById(Long id);

	public PrdEarlierRepaymentDate update(Long id, PrdProductDateDto dto) throws EavEntryNotFoundException;

	public List<PrdEarlierRepaymentDate> findByIdPrdProduct(Long idPrdProduct);

	public void delete(PrdEarlierRepaymentDate date);
}
