/**
 * 
 */
package com.synovia.digital.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author TeddyCouriol
 * @since 29 déc. 2016
 */
@Entity
@Table(name = "prd_user", schema = "test")
public class PrdUser {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "E_MAIL")
	private String email;
	@Column(name = "COMPANY")
	private String company;
	@Column(name = "PASSWORD")
	private String password;

	public PrdUser() {
	}

	/**
	 * 
	 * Constructs ... based on ...
	 *
	 * @param lastname
	 * @param firstname
	 * @param email
	 * @param company
	 * @param password
	 */
	public PrdUser(String lastname, String firstname, String email, String company, String password) {
		this.lastName = lastname;
		this.firstName = firstname;
		this.email = email;
		this.company = company;
		this.password = password;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
