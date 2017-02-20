/**
 * 
 */
package com.synovia.digital.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 16 janv. 2017
 */
@Configuration
@EnableGlobalAuthentication
public class ResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private EavUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//		System.out.println("ResourceSecurityConfiguration.configure(HttpSecurity)");
		//		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/api/**").authenticated().and().formLogin()
		//				.loginPage("/login").permitAll().and().logout().permitAll();

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/api/eavAccounts/{\\d+}/**").authenticated().and().formLogin().loginPage("/login")
				.permitAll().and().logout().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Here is an example of an override that could be done
		//			auth
		//					// enable in memory based authentication with a user named "user" and "admin"
		//					.inMemoryAuthentication().withUser("user").password("password").roles("USER").and()
		//					.withUser("admin").password("password").roles("USER", "ADMIN");

		System.out.println("ResourceSecurityConfiguration.configure(AuthenticationManagerBuilder)");
		auth.userDetailsService(this.userDetailsService).passwordEncoder(EavAccount.PASSWORD_ENCODER);

	}

}
