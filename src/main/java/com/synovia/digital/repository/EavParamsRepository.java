/**
 * 
 */
package com.synovia.digital.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.EavParams;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 mars 2017
 */
public interface EavParamsRepository extends CrudRepository<EavParams, Integer> {

	public List<EavParams> findByIsActive(Boolean isActive);
}
