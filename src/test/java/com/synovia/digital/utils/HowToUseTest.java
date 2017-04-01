/**
 * 
 */
package com.synovia.digital.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 31 mars 2017
 */
public class HowToUseTest {

	@Test
	public void testPageImpl() {
		List<Integer> aList = new ArrayList<>();
		int nbElements = 15;
		for (int i = 0; i < nbElements; i++) {
			aList.add(new Integer(i));
		}

		int nbElemPerPage = 5;
		int total = 67;
		int pageNb = 1;
		Page<Integer> pagedList = new PageImpl<>(aList, new PageRequest(pageNb - 1, nbElemPerPage, null),
				nbElemPerPage);

		// Print elements
		StringBuilder listBuilder = new StringBuilder("Liste initiale:\n");
		for (Integer i : aList) {
			listBuilder.append(i).append(" ");
		}
		System.out.println(listBuilder.toString());

		StringBuilder pageBuilder = new StringBuilder("Liste paginée:\n");
		for (Integer i : pagedList) {
			pageBuilder.append(i).append(" ");
		}
		System.out.println(pageBuilder.toString());
	}

	//	@Test
	public void testPaginate() {
		// Test 1
		int currentPg = 1;
		int totalPageNb = 15;

		paginate(currentPg, totalPageNb);

		// Test 2
		currentPg = 4;
		paginate(currentPg, totalPageNb);

		// Test 3
		currentPg = 8;
		paginate(currentPg, totalPageNb);

		// Test 4
		totalPageNb = 25;
		currentPg = 17;
		paginate(currentPg, totalPageNb);

		// Test 5
		totalPageNb = 25;
		currentPg = 25;
		paginate(currentPg, totalPageNb);
	}

	private void paginate(int currentPageNb, int totalPageNb) {
		System.out.println("Pagination: current=" + currentPageNb + ", total=" + totalPageNb);
		int sliceSize = 10;
		int beginPageNb = Math.max(1, currentPageNb - sliceSize / 2);
		int endPageNb = Math.min(beginPageNb + sliceSize, totalPageNb);

		for (int i = 0; i < endPageNb - beginPageNb + 1; i++) {
			int displayedNb = i + beginPageNb;
			Puce puce = new Puce(i, displayedNb);

			puce.active = (displayedNb == currentPageNb);
			System.out.print(puce);
		}

		System.out.println(" | ");
	}

	private class Puce {
		private boolean active;
		private int displayedNumber;
		private int pos;

		/**
		 * TODO Constructs ... based on ...
		 *
		 */
		public Puce(int position, int dispalyedNumber) {
			this.pos = position;
			this.displayedNumber = dispalyedNumber;
			this.active = false;
		}

		@Override
		public String toString() {
			String strActiveStart = active ? "( " : "";
			String strActiveEnd = active ? " )" : "";
			return new StringBuilder(" | ").append(strActiveStart).append(displayedNumber).append(strActiveEnd)
					.toString();
		}
	}
}
