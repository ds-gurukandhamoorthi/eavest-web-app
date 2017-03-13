/**
 * 
 */
package com.synovia.digital.service;

import org.springframework.stereotype.Service;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.PrdUser;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 26 janv. 2017
 */
@Service
public interface PrdUserService {

	public PrdUser getPrdUser(EavAccount account);
}
