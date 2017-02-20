/**
 * 
 */
package com.synovia.digital.model;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 7 févr. 2017
 */
//@Entity()
//@Table(name = "eav_account_role", schema = "test")
public class EavAccountRole {
	// ------------------------------ FIELDS ------------------------------
	//	@Id
	//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//	@Column(name = "ACCOUNT_ID")
	private Long idEavAccount;

	//	@Column(name = "ROLE_ID")
	private Integer idEavRole;

	// ------------------------------ CONSTRUCTORS ------------------------------

	public EavAccountRole() {

	}

	// ------------------------------ GETTERS/SETTERS ------------------------------

	public Long getIdEavAccount() {
		return this.idEavAccount;
	}

	public void setIdEavAccount(Long idEavAccount) {
		this.idEavAccount = idEavAccount;
	}

	public Integer getIdEavRole() {
		return this.idEavRole;
	}

	public void setIdEavRole(Integer idEavRole) {
		this.idEavRole = idEavRole;
	}
}
