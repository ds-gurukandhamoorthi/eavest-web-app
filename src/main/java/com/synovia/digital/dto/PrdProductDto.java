/**
 * 
 */
package com.synovia.digital.dto;

import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.synovia.digital.dto.utils.DtoDateFormat;
import com.synovia.digital.model.PrdSousJacent;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 13 févr. 2017
 */
public class PrdProductDto {

	private Long id;

	/**
	 * The identification number of the product (International Securities Identification
	 * Number)
	 */
	@NotEmpty
	private String isin;

	/** The label of the product */
	@NotEmpty
	private String label;

	/** The date of the end of the product [dd/MM/yyyy] */
	@NotBlank
	private Date dueDate;

	/** The date of the beginning of the product [dd/MM/yyyy] */
	private Date launchDate;

	/** The id of the underlying asset of the product */
	//	@NotNull
	//	private Long idSousJacent;

	/** The id of the underlying asset of the product */
	@NotNull
	private PrdSousJacent prdSousJacent;

	/** The id of the rule of how works the product. */
	private Long idRule;

	/** The date from which it is possible to subscribe to the product. [dd/MM/yyyy] */
	private Date subscriptionStartDate;

	/**
	 * The date from which it is no more possible to subscribe to the product.
	 * [dd/MM/yyyy]
	 */
	private Date subscriptionEndDate;

	/** The value of the coupon [%] */
	private Double couponValue;

	/** The nominal value of the product [EUR] */
	private Double nominalValue;

	/** A flag that indicates whether the capital is guaranteed. */
	@NotNull
	private Boolean capitalGuaranteed;

	/** The price at the beginning of the life of the product. */
	private Double startPrice;

	/** The company that delivers the product. */
	private String deliver;

	/** The company that guarantees the product. */
	private String guarantor;

	/** The effective end date of the product. */
	private Date endDate;

	/** The flag to determine whether the product is an EAVEST product. */
	private Boolean isEavest;

	/** The flag to determine whether a product is the bestseller of the moment. */
	private Boolean isBestSeller;

	/** The path to documents associated to the product. */
	private String path;

	public PrdProductDto() {

	}

	public String getIsin() {
		return this.isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setDueDate(String dueDate) throws ParseException {
		this.dueDate = DtoDateFormat.getFormat().parse(dueDate);
	}

	public Date getLaunchDate() {
		return this.launchDate;
	}

	public void setLaunchDate(Date creationDate) {
		this.launchDate = creationDate;
	}

	public void setLaunchDate(String creationDate) throws ParseException {
		this.launchDate = DtoDateFormat.getFormat().parse(creationDate);
	}

	public Date getSubscriptionStartDate() {
		return this.subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public void setSubscriptionStartDate(String subscriptionStartDate) throws ParseException {
		this.subscriptionStartDate = DtoDateFormat.getFormat().parse(subscriptionStartDate);
	}

	public Date getSubscriptionEndDate() {
		return this.subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public void setSubscriptionEndDate(String subscriptionEndDate) throws ParseException {
		this.subscriptionEndDate = DtoDateFormat.getFormat().parse(subscriptionEndDate);
	}

	public Double getCouponValue() {
		return this.couponValue;
	}

	public void setCouponValue(Double couponValue) {
		this.couponValue = couponValue;
	}

	public Double getNominalValue() {
		return this.nominalValue;
	}

	public void setNominalValue(Double nominalValue) {
		this.nominalValue = nominalValue;
	}

	public Boolean getCapitalGuaranteed() {
		return this.capitalGuaranteed;
	}

	public void setCapitalGuaranteed(Boolean capitalGuaranteed) {
		this.capitalGuaranteed = capitalGuaranteed;
	}

	public Double getStartPrice() {
		return this.startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public String getDeliver() {
		return this.deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public String getGuarantor() {
		return this.guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(String endDate) throws ParseException {
		this.endDate = DtoDateFormat.getFormat().parse(endDate);
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//	public Long getIdSousJacent() {
	//		return this.idSousJacent;
	//	}
	//
	//	public void setIdSousJacent(Long idSousJacent) {
	//		this.idSousJacent = idSousJacent;
	//	}

	public Long getIdRule() {
		return this.idRule;
	}

	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}

	public Boolean getIsEavest() {
		return this.isEavest;
	}

	public void setIsEavest(Boolean isEavest) {
		this.isEavest = isEavest;
	}

	public Boolean getIsBestSeller() {
		return this.isBestSeller;
	}

	public void setIsBestSeller(Boolean isBestSeller) {
		this.isBestSeller = isBestSeller;
	}

	public PrdSousJacent getPrdSousJacent() {
		return this.prdSousJacent;
	}

	public void setPrdSousJacent(PrdSousJacent prdSousJacent) {
		this.prdSousJacent = prdSousJacent;
	}
}
