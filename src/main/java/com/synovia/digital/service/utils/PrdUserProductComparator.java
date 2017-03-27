/**
 * 
 */
package com.synovia.digital.service.utils;

import java.util.Comparator;

import com.synovia.digital.model.PrdProduct;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 mars 2017
 */
public class PrdUserProductComparator implements Comparator<PrdProduct> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PrdProduct arg0, PrdProduct arg1) {

		return arg0.getLabel().compareTo(arg1.getLabel());
	}

}
