/**
 * 
 */
package com.synovia.digital.service.utils;

import java.util.Comparator;

import com.synovia.digital.model.PrdObservationDate;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 2 mars 2017
 */
public class UpcomingObservationDateComparator implements Comparator<PrdObservationDate> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PrdObservationDate arg0, PrdObservationDate arg1) {
		if (arg0.getDate().before(arg1.getDate()))
			return -1;

		if (arg0.getDate().after(arg1.getDate()))
			return 1;

		return 0;
	}

}
