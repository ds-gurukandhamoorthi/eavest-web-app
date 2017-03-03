/**
 * 
 */
package com.synovia.digital.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 25 févr. 2017
 */
public interface PrdObservationDateRepository extends JpaRepository<PrdObservationDate, Long> {

	public List<PrdObservationDate> findByPrdProduct(PrdProduct product);

	public List<PrdObservationDate> findByDateBetween(Date from, Date until);
}
