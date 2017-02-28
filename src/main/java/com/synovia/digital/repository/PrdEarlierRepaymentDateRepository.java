/**
 * 
 */
package com.synovia.digital.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdProduct;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
public interface PrdEarlierRepaymentDateRepository extends CrudRepository<PrdEarlierRepaymentDate, Long> {

	public List<PrdEarlierRepaymentDate> findByPrdProduct(PrdProduct product);
}
