/**
 * 
 */
package com.synovia.digital.dto;

import java.util.List;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 22 mars 2017
 */
public class PrdProductListDto {

	private List<PrdProductDto> productList;

	/**
	 * Default Constructors.
	 *
	 */
	public PrdProductListDto() {
	}

	public List<PrdProductDto> getProductList() {
		return this.productList;
	}

	public void setProductList(List<PrdProductDto> productList) {
		this.productList = productList;
	}

}
