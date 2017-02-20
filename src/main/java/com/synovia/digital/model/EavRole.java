/**
 * 
 */
package com.synovia.digital.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 janv. 2017
 */
@Entity
@Table(name = "eav_role", schema = "test")
public class EavRole {

	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "LABEL", nullable = false, unique = true)
	private String label;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToMany(mappedBy = "eavRoles")
	private Set<EavAccount> accounts;

	// ------------------------------ CONSTRUCTORS ------------------------------

	public EavRole() {

	}

	public EavRole(Integer id, String label, String description) {
		this.id = id;
		this.label = label;
		this.description = description;
	}

	// ------------------------------ GETTERS/SETTERS ------------------------------

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<EavAccount> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<EavAccount> accounts) {
		this.accounts = accounts;
	}
}
