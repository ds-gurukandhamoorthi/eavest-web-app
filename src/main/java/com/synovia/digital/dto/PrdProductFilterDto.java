/**
 * 
 */
package com.synovia.digital.dto;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 30 mars 2017
 */
public class PrdProductFilterDto {

	private String isin;

	private String label;

	private String deliver;

	private String labelSousJacent;

	private Boolean isEavest;

	/**
	 * TODO Constructs ... based on ...
	 *
	 */
	public PrdProductFilterDto() {
		this(null, null, null, null, null);
	}

	public PrdProductFilterDto(String isin, String label, String deliver, String labelSousJacent, Boolean isEavest) {
		this.isin = isin;
		this.label = label;
		this.deliver = deliver;
		this.labelSousJacent = labelSousJacent;
		this.isEavest = isEavest;
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

	public String getDeliver() {
		return this.deliver;
	}

	public String getLabelSousJacent() {
		return this.labelSousJacent;
	}

	public void setLabelSousJacent(String labelSousJacent) {
		this.labelSousJacent = labelSousJacent;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public Boolean getIsEavest() {
		return this.isEavest;
	}

	public void setIsEavest(Boolean isEavest) {
		this.isEavest = isEavest;
	}

	@Override
	public String toString() {
		return new StringBuilder("[").append("ISIN: ").append(isin).append(", Product name:").append(label)
				.append(", Bank name: ").append(deliver).append(", Sous-jacent: ").append(labelSousJacent)
				.append(", Produit Eavest: ").append(isEavest).append("]").toString();
	}
}
