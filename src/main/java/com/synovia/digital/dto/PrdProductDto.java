/**
 * 
 */
package com.synovia.digital.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.synovia.digital.dto.utils.DtoDateFormat;
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdRule;

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
	@NotBlank
	private String isin;

	/** The label of the product */
	@NotBlank
	private String label;

	/** The date of the end of the product [yyyy-MM-dd] */
	@NotNull
	@Size(max = 10)
	private String dueDate;

	/** The date of the beginning of the product [yyyy-MM-dd] */
	@NotNull
	@Size(max = 10)
	private String launchDate;

	/** The underlying asset of the product */
	private Long idPrdSousJacent;

	/** The list of observation dates referred by their id. */
	private String[] observationDates;

	/** */
	private String[] earlyRepaymentDates;

	private String[] couponPaymentDates;

	/** The date from which it is possible to subscribe to the product. [yyyy-MM-dd] */
	@Size(max = 10)
	private String subscriptionStartDate;

	/**
	 * The date from which it is no more possible to subscribe to the product.
	 * [yyyy-MM-dd]
	 */
	@Size(max = 10)
	private String subscriptionEndDate;

	/** The value of the coupon [%] */
	@Min(value = 0)
	@Max(value = 100)
	private Double couponValue;

	/** The nominal value of the product [EUR] */
	private Double nominalValue;

	/** A flag that indicates whether the capital is guaranteed. */
	private Boolean capitalGuaranteed;

	/** The price at the beginning of the life of the product [%]. */
	@Min(value = 0)
	@Max(value = 100)
	private Double startPrice;

	/** The company that delivers the product. */
	@Size(max = 40)
	private String deliver;

	/** The company that guarantees the product. */
	@Size(max = 40)
	private String guarantor;

	/** The product status code. */
	private String statusCode;

	/** The effective end date of the product. [yyyy-MM-dd] */
	@Size(max = 10)
	private String endDate;

	/** The flag to determine whether the product is an EAVEST product. */
	private Boolean isEavest;

	/** The flag to determine whether a product is the bestseller of the moment. */
	private Boolean isBestSeller;

	/** The path to documents associated to the product. */
	private String path;

	/** The strike of the product [-] */
	private Double strike;

	/** The frequency of the observation dates. */
	@Size(max = 40)
	private String observationFrequency;

	/** The reimbursement barrier [%] */
	@Min(value = 0)
	@Max(value = 100)
	private Double reimbursementBarrier;

	/** The protection barrier [%] */
	@Min(value = 0)
	@Max(value = 100)
	private Double protectionBarrier;

	/** The coupon barrier [%] */
	@Min(value = 0)
	@Max(value = 100)
	private Double couponBarrier;

	public PrdProductDto() {

	}

	public PrdProductDto(PrdProduct entity) {
		id = entity.getId();
		isin = entity.getIsin();
		label = entity.getLabel();
		DateFormat formatter = DtoDateFormat.getFormat();
		dueDate = entity.getDueDate() != null ? formatter.format(entity.getDueDate()) : null;
		launchDate = entity.getLaunchDate() != null ? formatter.format(entity.getLaunchDate()) : null;
		idPrdSousJacent = entity.getPrdSousJacent().getId();
		List<String> dates = new ArrayList<>();
		for (PrdObservationDate d : entity.getObservationDates()) {
			dates.add(formatter.format(d.getDate()));
		}
		observationDates = dates.toArray(new String[0]);
		dates = new ArrayList<>();
		for (PrdEarlierRepaymentDate d : entity.getEarlyRepaymentDates()) {
			dates.add(formatter.format(d.getDate()));
		}
		earlyRepaymentDates = dates.toArray(new String[0]);
		dates = new ArrayList<>();
		for (PrdCouponDate d : entity.getCouponPaymentDates()) {
			dates.add(formatter.format(d.getDate()));
		}
		couponPaymentDates = dates.toArray(new String[0]);
		subscriptionStartDate = entity.getSubscriptionStartDate() != null
				? formatter.format(entity.getSubscriptionStartDate()) : null;
		subscriptionEndDate = entity.getSubscriptionEndDate() != null
				? formatter.format(entity.getSubscriptionEndDate()) : null;
		couponValue = entity.getCouponValue();
		nominalValue = entity.getNominalValue();
		capitalGuaranteed = entity.getCapitalGuaranteed();
		startPrice = entity.getStartPrice();
		deliver = entity.getDeliver();
		guarantor = entity.getGuarantor();
		statusCode = entity.getPrdStatus().getCode();
		endDate = entity.getEndDate() != null ? formatter.format(entity.getEndDate()) : null;
		isEavest = entity.getIsEavest();
		isBestSeller = entity.getIsBestSeller();
		path = entity.getPath();
		strike = entity.getStrike();
		observationFrequency = entity.getObservationFrequency();
		PrdRule rule = entity.getPrdRule();
		if (rule != null) {
			reimbursementBarrier = rule.getReimbursementBarrier();
			protectionBarrier = rule.getProtectionBarrier();
			couponBarrier = rule.getCouponBarrier();
		}
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

	public String getDueDate() {
		return this.dueDate;
	}

	public Date getDueDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.dueDate);
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getLaunchDate() {
		return this.launchDate;
	}

	public Date getLaunchDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.launchDate);
	}

	public void setLaunchDate(String creationDate) {
		this.launchDate = creationDate;
	}

	public String getSubscriptionStartDate() {
		return this.subscriptionStartDate;
	}

	public Date getSubscriptionStartDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.subscriptionStartDate);
	}

	public void setSubscriptionStartDate(String subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public String getSubscriptionEndDate() {
		return this.subscriptionEndDate;
	}

	public Date getSubscriptionEndDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.subscriptionEndDate);
	}

	public void setSubscriptionEndDate(String subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
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

	public String getEndDate() {
		return this.endDate;
	}

	public Date getEndDateObject() throws ParseException {
		return DtoDateFormat.getFormat().parse(this.endDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public Double getStrike() {
		return this.strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}

	public String getObservationFrequency() {
		return this.observationFrequency;
	}

	public void setObservationFrequency(String observationFrequency) {
		this.observationFrequency = observationFrequency;
	}

	public Double getProtectionBarrier() {
		return this.protectionBarrier;
	}

	public void setProtectionBarrier(Double protectionBarrier) {
		this.protectionBarrier = protectionBarrier;
	}

	public Double getCouponBarrier() {
		return this.couponBarrier;
	}

	public void setCouponBarrier(Double couponBarrier) {
		this.couponBarrier = couponBarrier;
	}

	public Long getIdPrdSousJacent() {
		return this.idPrdSousJacent;
	}

	public void setIdPrdSousJacent(Long idPrdSousJacent) {
		this.idPrdSousJacent = idPrdSousJacent;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String[] getObservationDates() {
		return this.observationDates;
	}

	public void setObservationDates(String[] observationDates) {
		this.observationDates = observationDates;
	}

	public String[] getEarlyRepaymentDates() {
		return this.earlyRepaymentDates;
	}

	public void setEarlyRepaymentDates(String[] earlyRepaymentDates) {
		this.earlyRepaymentDates = earlyRepaymentDates;
	}

	public String[] getCouponPaymentDates() {
		return this.couponPaymentDates;
	}

	public void setCouponPaymentDates(String[] couponPaymentDates) {
		this.couponPaymentDates = couponPaymentDates;
	}

	public Double getReimbursementBarrier() {
		return this.reimbursementBarrier;
	}

	public void setReimbursementBarrier(Double reimbursementBarrier) {
		this.reimbursementBarrier = reimbursementBarrier;
	}

}
