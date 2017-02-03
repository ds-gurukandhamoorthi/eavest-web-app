/**
 * 
 */
package com.synovia.digital.domain;

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
public class EavRole extends AbstractBean {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODE", nullable = false)
	private String code;

	@ManyToMany(mappedBy = "roles")
	private Set<EavAccount> accounts;
}
