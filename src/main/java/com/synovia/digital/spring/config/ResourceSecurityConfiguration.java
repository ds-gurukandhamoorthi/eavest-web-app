/**
 * 
 */
package com.synovia.digital.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 16 janv. 2017
 */
@Configuration
@EnableGlobalAuthentication
public class ResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/api/**").authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
	}
}
