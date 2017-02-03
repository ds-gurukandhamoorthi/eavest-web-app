/**
 * 
 */
package com.synovia.digital.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 janv. 2017
 */
@Entity
@Table(name = "eav_account", schema = "test")
public class EavAccount extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// ---- Authentication fields ----
	@NotNull
	@Column(name = "E_MAIL", unique = true, nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	@JsonIgnore
	private String password;

	@Column(name = "RESET_PWD_TOKEN")
	private String resetPasswordToken;

	@Column(name = "RESET_PWD_EXPIRATION_DATE")
	private Date resetPasswordExpires;

	// ---- Account management fields ----
	@Column(name = "ENABLED")
	private Boolean enabled = true;

	@Column(name = "ROLES")
	private String[] roles;

	// ---- Personal information fields ----
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "ALIAS_NAME")
	private String aliasName;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Column(name = "FIX_PHONE")
	private String fixPhone;

	@Column(name = "NEWSLETTER")
	private Boolean newsletter = false;

	// ---- Core Business fields ----
	@OneToOne(mappedBy = "account", optional = true)
	private PrdUser prdUser;

	// ---- Other fields ----
	@Transient
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	// ------------------------------ CONSTRUCTORS ------------------------------

	public EavAccount() {

	}

	/**
	 * 
	 * TODO Constructs an EAV account based on an email as an identifier, a password, a
	 * last name and first name.
	 *
	 * @param mail
	 * @param password
	 * @param lastName
	 * @param firstName
	 */
	public EavAccount(String mail, String password, String lastName, String firstName, String... roles) {
		System.out.println("EavAccount.EavAccount(mail, password, lastName, firstName, roles): " + mail + ", "
				+ password + ", " + lastName + ", " + firstName + ", " + roles);
		this.email = mail;
		this.setPassword(password);
		this.setLastName(lastName);
		this.firstName = firstName;
		this.roles = roles;

	}

	// ------------------------------ GETTERS/SETTERS ------------------------------

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		System.out.println("EavAccount.setPassword()");
		if (!StringUtils.isBlank(password)) {
			this.password = PASSWORD_ENCODER.encode(password);
		} else {
			this.password = password;
		}
	}

	@Transient
	public void setEncryptedPassword(String encryptedPassword) {
		this.password = encryptedPassword;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAliasName() {
		return this.aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFixPhone() {
		return this.fixPhone;
	}

	public void setFixPhone(String fixPhone) {
		this.fixPhone = fixPhone;
	}

	public Boolean getNewsletter() {
		return this.newsletter;
	}

	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public PrdUser getPrdUser() {
		return this.prdUser;
	}

	public void setPrdUser(PrdUser prdUser) {
		this.prdUser = prdUser;
	}

	public String[] getRoles() {
		return this.roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getResetPasswordToken() {
		return this.resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Date getResetPasswordExpires() {
		return this.resetPasswordExpires;
	}

	public void setResetPasswordExpires(Date resetPasswordExpires) {
		this.resetPasswordExpires = resetPasswordExpires;
	}
}
