package com.synovia.digital.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "prd_product", schema = "test")
public class PrdProduct extends AbstractBean {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "LABEL")
	private String label;

	@Column(name = "DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@JoinColumn(name = "ID_PRD_SOUSJACENT", referencedColumnName = "ID", nullable = false)
	@ManyToOne
	private PrdSousJacent idPrdSousJacent;

	@JoinColumn(name = "ID_PRD_RULE", referencedColumnName = "ID")
	@OneToOne()
	private PrdRule idPrdRule;

	@OneToMany(mappedBy = "idPrdProduct")
	private Set<PrdWatchingDate> watchingDates;

	@OneToMany(mappedBy = "idPrdProduct")
	private Set<PrdEarlierRepaymentDate> earlyRepaymentDates;

	@OneToMany(mappedBy = "idPrdProduct")
	private Set<PrdCouponDate> couponPaymentDates;

	@Column(name = "SUBSCRIBE_START_DATE")
	private Date subscriptionStartDate;

	@Column(name = "SUBSCRIBE_END_DATE")
	private Date subscriptionEndDate;

	@Column(name = "COUPON", precision = 2)
	private Double couponValue;

	@Column(name = "NOMINAL")
	private Double nominalValue;

	@Column(name = "CAPITAL_GUARANTEED")
	private Boolean capitalGuaranteed;

	@Column(name = "START_PRICE")
	private Double startPrice;

	@Column(name = "DELIVER")
	private String deliver;

	@Column(name = "GUARANTOR")
	private String guarantor;

	@Transient
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public PrdProduct() {
	}

	/**
	 * 
	 * @param label
	 * @param dueDate
	 * @param dateOfCreation
	 * @param idSousJacent
	 * @param idRule
	 * @param subscriptionStart
	 * @param subscriptionEnd
	 * @param coupon
	 * @param nominalValue
	 * @param isGuaranteed
	 * @param deliver
	 * @param guarantor
	 * @param startPrice
	 */
	public PrdProduct(String label, Date dueDate, Date dateOfCreation, PrdSousJacent idSousJacent, PrdRule idRule,
			Date subscriptionStart, Date subscriptionEnd, Double coupon, Double nominalValue, Boolean isGuaranteed,
			String deliver, String guarantor, Double startPrice) {
		this.label = label;
		this.dueDate = dueDate;
		this.creationDate = dateOfCreation;
		this.idPrdSousJacent = idSousJacent;
		this.idPrdRule = idRule;
		this.subscriptionEndDate = subscriptionEnd;
		this.subscriptionStartDate = subscriptionStart;
		this.couponValue = coupon;
		this.nominalValue = nominalValue;
		this.capitalGuaranteed = isGuaranteed;
		this.deliver = deliver;
		this.guarantor = guarantor;
		this.startPrice = startPrice;
	}

	/**
	 * 
	 * @param label
	 * @param dueDate
	 * @param dateOfCreation
	 * @param idSousJacent
	 * @param idRule
	 * @param subscriptionStart
	 * @param subscriptionEnd
	 * @param coupon
	 * @param nominalValue
	 * @param isGuaranteed
	 * @param deliver
	 * @param guarantor
	 * @param startPrice
	 * @throws ParseException
	 */
	public PrdProduct(String label, String dueDate, String dateOfCreation, PrdSousJacent idSousJacent, PrdRule idRule,
			String subscriptionStart, String subscriptionEnd, Double coupon, Double nominalValue, Boolean isGuaranteed,
			String deliver, String guarantor, Double startPrice) throws ParseException {
		this(label, (Date) null, (Date) null, idSousJacent, idRule, (Date) null, (Date) null, coupon, nominalValue,
				isGuaranteed, deliver, guarantor, startPrice);
		this.dueDate = format.parse(dueDate);
		this.creationDate = format.parse(dateOfCreation);
		this.subscriptionEndDate = format.parse(subscriptionEnd);
		this.subscriptionStartDate = format.parse(subscriptionStart);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getDueDateAsString() {
		return format.format(dueDate);
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getCreationDateAsString() {
		return format.format(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public PrdSousJacent getIdPrdSousJacent() {
		return idPrdSousJacent;
	}

	public void setIdPrdSousJacent(PrdSousJacent idPrdSousJacent) {
		this.idPrdSousJacent = idPrdSousJacent;
	}

	public PrdRule getIdPrdRule() {
		return idPrdRule;
	}

	public void setIdPrdRule(PrdRule idPrdRule) {
		this.idPrdRule = idPrdRule;
	}

	public Set<PrdWatchingDate> getWatchingDates() {
		return watchingDates;
	}

	public void setWatchingDates(Set<PrdWatchingDate> watchingDates) {
		this.watchingDates = watchingDates;
	}

	public Set<PrdEarlierRepaymentDate> getEarlyRepaymentDates() {
		return earlyRepaymentDates;
	}

	public void setEarlyRepaymentDates(Set<PrdEarlierRepaymentDate> earlyRepaymentDates) {
		this.earlyRepaymentDates = earlyRepaymentDates;
	}

	public Set<PrdCouponDate> getCouponPaymentDates() {
		return couponPaymentDates;
	}

	public void setCouponPaymentDates(Set<PrdCouponDate> couponPaymentDates) {
		this.couponPaymentDates = couponPaymentDates;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public String getSubscriptionStartDateAsString() {
		return format.format(subscriptionStartDate);
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public String getSubscriptionEndDateAsString() {
		return format.format(subscriptionEndDate);
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Double getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(Double couponValue) {
		this.couponValue = couponValue;
	}

	public Boolean getCapitalGuaranteed() {
		return capitalGuaranteed;
	}

	public void setCapitalGuaranteed(Boolean capitalGuaranteed) {
		this.capitalGuaranteed = capitalGuaranteed;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public Double getNominalValue() {
		return nominalValue;
	}

	public void setNominalValue(Double nominalValue) {
		this.nominalValue = nominalValue;
	}
}
