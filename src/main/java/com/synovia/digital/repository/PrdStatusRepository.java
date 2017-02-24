/**
 * 
 */
package com.synovia.digital.repository;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.PrdStatus;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 17 févr. 2017
 */
public interface PrdStatusRepository extends CrudRepository<PrdStatus, Integer> {

	public PrdStatus findByCode(String code);
}
