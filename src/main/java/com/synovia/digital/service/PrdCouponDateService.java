/**
 * 
 */
package com.synovia.digital.service;

import java.util.List;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdCouponDate;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
public interface PrdCouponDateService {

	public PrdCouponDate add(PrdProductDateDto dto) throws EavEntryNotFoundException;

	public void delete(Long id) throws EavEntryNotFoundException;

	public void delete(PrdCouponDate entity);

	public PrdCouponDate findById(Long id);

	public PrdCouponDate update(Long id, PrdProductDateDto dto) throws EavEntryNotFoundException;

	public List<PrdCouponDate> findByIdPrdProduct(Long idPrdProduct);

}
