/**
 * 
 */
package com.synovia.digital.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author TeddyCouriol
 * @since 29 déc. 2016
 */
@Entity
@Table(name = "prd_user", schema = "test")
public class PrdUser {
	// ------------------------------ FIELDS ------------------------------
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "ID_ACCOUNT", unique = true, nullable = false, updatable = false)
	private EavAccount account;

	@ManyToMany()
	@JoinTable(name = "PRD_USER_PRODUCT",
			joinColumns = @JoinColumn(name = "ID_PRD_USER", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "ID_PRD_PRODUCT", referencedColumnName = "ID"))
	private Set<PrdProduct> products;

	// ------------------------------ CONSTRUCTORS ------------------------------

	public PrdUser() {
		this(null);
	}

	public PrdUser(EavAccount account) {
		System.out.println("PrdUser.PrdUser()");
		this.account = account;
	}

	// ------------------------------ GETTERS/SETTERS ------------------------------

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EavAccount getAccount() {
		return this.account;
	}

	public void setAccount(EavAccount account) {
		this.account = account;
	}

	public Set<PrdProduct> getProducts() {
		return this.products;
	}

	public void setProducts(Set<PrdProduct> products) {
		this.products = products;
	}

}
