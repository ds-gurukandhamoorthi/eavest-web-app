/**
 * 
 */
package com.synovia.digital.spring.config;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 16 janv. 2017
 */
@Configuration
@EnableGlobalAuthentication
public class JdbcSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
		System.out.println("JdbcSecurityConfiguration.userDetailsService()");
		RowMapper<User> userRowMapper = (ResultSet rs, int i) -> new User(rs.getString("E_MAIL"),
				rs.getString("PASSWORD"), rs.getBoolean("ENABLED"), rs.getBoolean("ENABLED"), rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"), AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));

		return username -> jdbcTemplate.queryForObject("SELECT * from test.EAV_ACCOUNT where E_MAIL = ?", userRowMapper,
				username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.authentication.configurers.
	 * GlobalAuthenticationConfigurerAdapter#init(org.springframework.security.config.
	 * annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("JdbcSecurityConfiguration.init()");
		auth.userDetailsService(this.userDetailsService);
	}
}
