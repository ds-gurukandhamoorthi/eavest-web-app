/**
 * 
 */
package com.synovia.digital.service;

import java.util.Date;
import java.util.List;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdProduct;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 14 févr. 2017
 */
public interface PrdProductService {

	public PrdProduct findById(Long id) throws EavEntryNotFoundException;

	public Iterable<PrdProduct> findAll();

	public PrdProduct add(PrdProductDto dto) throws EavDuplicateEntryException;

	public List<PrdProduct> listRefundProducts(Date from);
}
