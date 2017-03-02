/**
 * 
 */
package com.synovia.digital.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 25 févr. 2017
 */
public interface PrdObservationDateRepository extends CrudRepository<PrdObservationDate, Long> {

	public List<PrdObservationDate> findByPrdProduct(PrdProduct product);
}