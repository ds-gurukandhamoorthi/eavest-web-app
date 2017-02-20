/**
 * 
 */
package com.synovia.digital.repository;

import java.util.List;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.EavAccountRole;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 févr. 2017
 */
public interface EavAccountRoleRepository /*
											 * extends CrudRepository<EavAccountRole, Long>
											 */ {

	public List<EavAccountRole> findByIdEavRole(Integer idEavRole);

	public List<EavAccount> findIdEavAccountByIdEavRole(Integer idEavRole);
}
