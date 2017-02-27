/**
 * 
 */
package com.synovia.digital.service;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 févr. 2017
 */
public interface EavAccountService {

	public EavAccount findByEmail(String username);
}
