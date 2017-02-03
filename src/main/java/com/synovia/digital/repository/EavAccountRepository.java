/**
 * 
 */
package com.synovia.digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synovia.digital.domain.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 janv. 2017
 */
public interface EavAccountRepository extends JpaRepository<EavAccount, Long> {

	public EavAccount findByEmail(String email);

	public EavAccount findByResetPasswordToken(String token);
}
