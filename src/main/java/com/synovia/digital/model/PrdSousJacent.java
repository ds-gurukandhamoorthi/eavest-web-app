package com.synovia.digital.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "prd_sousjacent", schema = "test")
@Entity
public class PrdSousJacent extends AbstractBean {
	// ------------------------------ FIELDS ------------------------------

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "LABEL", nullable = false, unique = true, length = 50)
	private String label;

	@Column(name = "ISIN_CODE", length = 25)
	private String isinCode;

	@Column(name = "BLOOMBERG_CODE", unique = true, length = 25)
	private String bloombergCode;

	@Column(name = "IS_NEW")
	private Boolean isNew = true;

	@Column(name = "IS_PERF_REVIEW")
	private Boolean isPerfReview = false;

	@Column(name = "MONTH_VALUE")
	private Double monthValue;

	@Column(name = "MONTH_PERF")
	private Double perfOnAMonth;

	@Column(name = "YEAR_PERF")
	private Double perfOnAYear;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prdSousJacent")
	private List<PrdSousJacentValue> prdSousJacentValues;

	@OneToMany(mappedBy = "prdSousJacent")
	private Set<PrdProduct> prdProducts;

	// --------------------- CONSTRUCTOR(S) ---------------------

	public PrdSousJacent() {
		this.prdProducts = new HashSet<>();
		this.prdSousJacentValues = new ArrayList<>();

	}

	public PrdSousJacent(String label) {
		this.label = label;
	}

	public PrdSousJacent(String label, List<PrdSousJacentValue> values) {
		this.label = label;
		this.prdSousJacentValues = values;
	}

	// --------------------- GETTER / SETTER METHODS ---------------------

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

	public Set<PrdProduct> getPrdProducts() {
		return this.prdProducts;
	}

	public void setPrdProducts(Set<PrdProduct> prdProducts) {
		this.prdProducts = prdProducts;
	}

	public List<PrdSousJacentValue> getPrdSousJacentValues() {
		return this.prdSousJacentValues;
	}

	public void setPrdSousJacentValues(List<PrdSousJacentValue> prdSousJacentValues) {
		this.prdSousJacentValues = prdSousJacentValues;
	}

	public String getIsinCode() {
		return this.isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public Boolean getIsNew() {
		return this.isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getBloombergCode() {
		return this.bloombergCode;
	}

	public void setBloombergCode(String bloombergCode) {
		this.bloombergCode = bloombergCode;
	}

	public Boolean getIsPerfReview() {
		return this.isPerfReview;
	}

	public void setIsPerfReview(Boolean isPerfReview) {
		this.isPerfReview = isPerfReview;
	}

	public Double getMonthValue() {
		return this.monthValue;
	}

	public void setMonthValue(Double monthValue) {
		this.monthValue = monthValue;
	}

	public Double getPerfOnAMonth() {
		return this.perfOnAMonth;
	}

	public void setPerfOnAMonth(Double perfOnAMonth) {
		this.perfOnAMonth = perfOnAMonth;
	}

	public Double getPerfOnAYear() {
		return this.perfOnAYear;
	}

	public void setPerfOnAYear(Double perfOnAYear) {
		this.perfOnAYear = perfOnAYear;
	}

	// --------------------- OTHER METHODS ---------------------

	@Override
	public String toString() {
		String strId = id != null ? id.toString() : null;
		String strIsNew = isNew != null ? isNew.toString() : null;

		return new StringBuilder("[").append("ID:").append(strId).append("; ").append("Label:").append(label)
				.append("; ").append("ISIN:").append(isinCode).append("; ").append("New base:").append(strIsNew)
				.append("; ").append("Displayable in Perf Review:").append(isPerfReview).append("; ")
				.append("BLOOMBERG code:").append(bloombergCode).append("; ").append("Current month value:")
				.append(monthValue).append("; ").append("Current perf on a month:").append(perfOnAMonth).append("; ")
				.append("Current perf on a year:").append(perfOnAYear).append("]").toString();
	}

}
