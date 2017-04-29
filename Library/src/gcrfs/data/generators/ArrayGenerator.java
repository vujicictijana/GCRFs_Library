package gcrfs.data.generators;

import java.util.Random;

public class ArrayGenerator {

	/**
	 * Returns an array of n random generated double values.
	 *
	 * @param noOfElements
	 *            number of elements in the array
	 * @param maximum
	 *            the bound on the random number
	 * @return the resulting array of double values
	 */
	public static double[] generateArray(int noOfElements, int maximum) {
		double[] r = new double[noOfElements];
		Random rand = new Random();
		for (int i = 0; i < r.length; i++) {
			r[i] = rand.nextInt(maximum) + 1 + Math.random();
		}
		return r;
	}
}
