package app.impl;

import java.util.Comparator;

/**
 * DO NOT EDIT THIS CLASS
 * Implements the Comparator interface to compare two given integers
 */
public class IntegerComparator implements Comparator<Integer> {

	/**
	 * Returns a negative number, zero, or a positive number if the first argument is less than, equal to or greater than
	 * the second argument respectively.
	 */
	public int compare(Integer o1, Integer o2) {
		return o1 - o2;
	}
	
}
