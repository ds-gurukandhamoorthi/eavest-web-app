/**
 * 
 */
package com.synovia.digital.repository;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.EavRole;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 févr. 2017
 */
public interface EavRoleRepository extends CrudRepository<EavRole, Integer> {

	public EavRole findByLabel(String label);
}
