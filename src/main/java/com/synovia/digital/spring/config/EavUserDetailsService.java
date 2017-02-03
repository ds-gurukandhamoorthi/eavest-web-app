/**
 * 
 */
package com.synovia.digital.spring.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.synovia.digital.domain.EavAccount;
import com.synovia.digital.repository.EavAccountRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 29 janv. 2017
 */
@Component
public class EavUserDetailsService implements UserDetailsService {

	private final EavAccountRepository repo;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public EavUserDetailsService(EavAccountRepository repo) {
		this.repo = repo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername
	 * (java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("EavUserDetailsService.loadUserByUsername()");
		EavAccount account = repo.findByEmail(username);
		UserDetails details = new User(account.getEmail(), account.getPassword(), account.getEnabled(), true, true,
				true, AuthorityUtils.createAuthorityList(account.getRoles()));
		return details;
	}

}
