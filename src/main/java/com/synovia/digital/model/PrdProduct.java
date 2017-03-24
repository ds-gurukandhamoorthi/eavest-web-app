package com.synovia.digital.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.context.i18n.LocaleContextHolder;

import com.synovia.digital.utils.EavUtils;

@Entity
@Table(name = "prd_product", schema = "test")
public class PrdProduct extends AbstractBean {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ISIN", nullable = false, unique = true)
	private String isin;

	@Column(name = "LABEL", nullable = false)
	private String label;

	@Column(name = "LAUNCH_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date launchDate;

	@Column(name = "DUE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@JoinColumn(name = "ID_PRD_SOUSJACENT", referencedColumnName = "ID")
	@ManyToOne
	private PrdSousJacent prdSousJacent;

	@Embedded
	private PrdRule prdRule;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prdProduct")
	private Set<PrdObservationDate> observationDates;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prdProduct")
	private Set<PrdEarlierRepaymentDate> earlyRepaymentDates;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prdProduct")
	private Set<PrdCouponDate> couponPaymentDates;

	@Column(name = "SUBSCRIBE_START_DATE")
	@Temporal(TemporalType.DATE)
	private Date subscriptionStartDate;

	@Column(name = "SUBSCRIBE_END_DATE")
	@Temporal(TemporalType.DATE)
	private Date subscriptionEndDate;

	@Column(name = "COUPON", precision = 2)
	private Double couponValue;

	@Column(name = "NOMINAL")
	private Double nominalValue;

	@Column(name = "CAPITAL_GUARANTEED")
	private Boolean capitalGuaranteed;

	@Column(name = "START_PRICE")
	/** Translation of "prix d'emission" */
	private Double startPrice;

	@Column(name = "DELIVER")
	private String deliver;

	@Column(name = "GUARANTOR")
	private String guarantor;

	/** The Status of this entity. Is updated in post-update method. */
	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_PRD_STATUS", referencedColumnName = "ID")
	private PrdStatus prdStatus;

	@ManyToMany(mappedBy = "products")
	private Set<PrdUser> prdUsers;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "IS_EAVEST")
	private Boolean isEavest = false;

	@Column(name = "BEST_SELLER")
	private Boolean isBestSeller = false;

	@Column(name = "PATH")
	private String path;

	@Column(name = "STRIKE")
	private Double strike;

	@Column(name = "OBSERVATION_FREQUENCY")
	private String observationFrequency;

	@Column(name = "IMAGE_SHORTCUT")
	private String imageShortcut;

	@Transient
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public PrdProduct() {
		this.prdRule = new PrdRule();
		this.observationDates = new HashSet<>();
		this.earlyRepaymentDates = new HashSet<>();
		this.couponPaymentDates = new HashSet<>();
	}

	/**
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
		this();
		this.label = label;
		this.dueDate = dueDate;
		this.launchDate = dateOfCreation;
		this.prdSousJacent = idSousJacent;
		this.prdRule = idRule;
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
		this.launchDate = format.parse(dateOfCreation);
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

	public String getDueDateAsLongFormatString() {
		DateFormat format = new SimpleDateFormat(EavUtils.LONG_DATE_FORMAT_PATTERN, LocaleContextHolder.getLocale());
		return format.format(dueDate);

	}

	public String getDueDateAsStringLongFormat() {
		return format.format(dueDate);
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setDueDateAsString(String dueDate) throws ParseException {
		if (dueDate != null) {
			this.dueDate = format.parse(dueDate);
		}
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public String getLaunchDateAsString() {
		return format.format(launchDate);
	}

	public void setLaunchDate(Date creationDate) {
		this.launchDate = creationDate;
	}

	public void setLaunchDateAsString(String creationDate) throws ParseException {
		if (creationDate != null) {
			this.launchDate = format.parse(creationDate);
		}
	}

	public PrdSousJacent getPrdSousJacent() {
		return prdSousJacent;
	}

	public void setPrdSousJacent(PrdSousJacent idPrdSousJacent) {
		this.prdSousJacent = idPrdSousJacent;
	}

	public PrdRule getPrdRule() {
		return prdRule;
	}

	public void setPrdRule(PrdRule idPrdRule) {
		this.prdRule = idPrdRule;
	}

	public Set<PrdObservationDate> getWatchingDates() {
		return observationDates;
	}

	public void setWatchingDates(Set<PrdObservationDate> watchingDates) {
		this.observationDates = watchingDates;
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

	public void setSubscriptionStartDateAsString(String subscriptionStartDate) throws ParseException {
		this.subscriptionStartDate = format.parse(subscriptionStartDate);
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

	public void setSubscriptionEndDateAsString(String subscriptionEndDate) throws ParseException {
		this.subscriptionEndDate = format.parse(subscriptionEndDate);

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

	public String getIsin() {
		return this.isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Set<PrdObservationDate> getObservationDates() {
		return this.observationDates;
	}

	public void setObservationDates(Set<PrdObservationDate> observationDates) {
		this.observationDates = observationDates;
	}

	public Set<PrdUser> getPrdUsers() {
		return this.prdUsers;
	}

	public void setPrdUsers(Set<PrdUser> prdUsers) {
		this.prdUsers = prdUsers;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDateAsString() {
		return format.format(endDate);
	}

	public void setEndDateAsString(String endDate) throws ParseException {
		this.endDate = format.parse(endDate);
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Double getStrike() {
		return this.strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
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

	public String getObservationFrequency() {
		return this.observationFrequency;
	}

	public void setObservationFrequency(String observationFrequency) {
		this.observationFrequency = observationFrequency;
	}

	public PrdStatus getPrdStatus() {
		return this.prdStatus;
	}

	public void setPrdStatus(PrdStatus prdStatus) {
		this.prdStatus = prdStatus;
	}

	public String getImageShortcut() {
		return this.imageShortcut;
	}

	public void setImageShortcut(String imageShortcut) {
		this.imageShortcut = imageShortcut;
	}

	// -------------------------- OTHER METHODS --------------------------

	@Override
	public String toString() {
		String strLaunchDate = this.launchDate != null ? format.format(this.launchDate) : null;
		String strDueDate = this.dueDate != null ? format.format(dueDate) : null;
		String strPrdSousJacent = this.prdSousJacent != null ? prdSousJacent.toString() : null;
		String strPrdRule = this.prdRule != null ? prdRule.toString() : null;
		String strSubscribeStartDate = subscriptionStartDate != null ? format.format(subscriptionStartDate) : null;
		String strSubscribeEndDate = subscriptionEndDate != null ? format.format(subscriptionEndDate) : null;
		String strPrdStatus = prdStatus != null ? prdStatus.toString() : null;
		String strEndDate = endDate != null ? format.format(endDate) : null;

		return new StringBuilder("[").append("ID:").append(this.id).append("; ").append("ISIN:").append(this.isin)
				.append("; ").append("Label:").append(this.label).append("; ").append("Launch date:")
				.append(strLaunchDate).append("; ").append("Due date:").append(strDueDate).append("; ").append("Base:")
				.append(strPrdSousJacent).append("; ").append("Rule:").append(strPrdRule).append("; ")
				.append("Subscription start date:").append(strSubscribeStartDate).append("; ")
				.append("Subscription end date:").append(strSubscribeEndDate).append("; ").append("Coupon value:")
				.append(this.couponValue).append("; ").append("Nominal value:").append(this.nominalValue).append("; ")
				.append("Capital guaranteed:").append(this.capitalGuaranteed).append("; ").append("Start price:")
				.append(this.startPrice).append("; ").append("Bank:").append(this.deliver).append("; ")
				.append("Guarantor:").append(this.guarantor).append("; ").append("Status:").append(strPrdStatus)
				.append("; ").append("End date:").append(strEndDate).append("; ").append("EAVEST product:")
				.append(this.isEavest).append("; ").append("Best-seller:").append(this.isBestSeller).append("; ")
				.append("Path:").append(this.path).append("; ").append("Strike:").append(this.strike).append("; ")
				.append("Observation frequency:").append(this.observationFrequency).append("; ")
				.append("Image shortcut:").append(this.imageShortcut).append("]").toString();
	}

}
