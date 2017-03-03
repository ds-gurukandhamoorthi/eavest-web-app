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
 * @since 2 mars 2017
 */
public class RefundProductComparator implements Comparator<PrdProduct> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PrdProduct arg0, PrdProduct arg1) {
		if (arg0.getEndDate().after(arg1.getEndDate()))
			return -1;

		if (arg0.getEndDate().before(arg1.getEndDate()))
			return 1;

		return 0;
	}

}
