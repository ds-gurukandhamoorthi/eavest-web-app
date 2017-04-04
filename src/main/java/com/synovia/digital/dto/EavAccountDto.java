/**
 * 
 */
package com.synovia.digital.dto;

import javax.validation.constraints.Size;

import com.synovia.digital.model.EavAccount;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 4 avr. 2017
 */
public class EavAccountDto {

	@Size(max = 50)
	private String lastName;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String aliasName;

	@Size(max = 100)
	private String company;

	@Size(max = 50)
	private String country;

	@Size(max = 50)
	private String mobilePhone;

	@Size(max = 50)
	private String fixPhone;

	private Boolean enabled;

	private Boolean newsletter;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public EavAccountDto() {
	}

	public EavAccountDto(EavAccount account) {
		lastName = account.getLastName();
		firstName = account.getFirstName();
		aliasName = account.getAliasName();
		company = account.getCompany();
		country = account.getCountry();
		mobilePhone = account.getMobilePhone();
		fixPhone = account.getFixPhone();
		newsletter = account.getNewsletter();
		enabled = account.getEnabled();

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

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	@Override
	public String toString() {
		return new StringBuilder("[").append("Last name: ").append(lastName).append(", ").append("First name: ")
				.append(firstName).append(", ").append("Alias: ").append(aliasName).append(", ").append("Company: ")
				.append(company).append(", ").append("Country: ").append(country).append(", ").append("Mobile phone: ")
				.append(mobilePhone).append(", ").append("Fix phone: ").append(fixPhone).append(", ")
				.append("Newsletter: ").append(newsletter).append("]").toString();
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
