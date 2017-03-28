/**
 * 
 */
package com.synovia.digital.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdSousJacentValue;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 15 févr. 2017
 */
public interface PrdSousJacentValueRepository extends CrudRepository<PrdSousJacentValue, Long> {

	public List<PrdSousJacentValue> findByPrdSousJacentAndDateBetween(PrdSousJacent ssjct, Date start, Date end);

	public List<PrdSousJacentValue> findByPrdSousJacentAndDate(PrdSousJacent ssjct, Date date);
}
