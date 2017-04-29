package gcrfs.data.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayReader {
	/**
	 * Reads array from the given file.
	 *
	 * @param path
	 *            path to file
	 * @param noOfElements
	 *            number of elements in the array
	 * @return the array of double values
	 */
	public static double[] readArray(String path, int noOfElements) {
		double[] r = new double[noOfElements];

		String[] text = ArrayReader.read(path);
		if (text.length != noOfElements) {
			return null;
		}
		for (int k = 0; k < text.length; k++) {
			r[k] = Double.parseDouble(text[k]);
		}
		return r;
	}

	/**
	 * Reads array from the given file.
	 *
	 * @param path
	 *            path to file
	 * @return the array of double values
	 */
	public static double[] readArray(String path) {

		String[] text = ArrayReader.read(path);
		double[] r = new double[text.length];

		for (int k = 0; k < text.length; k++) {
			r[k] = Double.parseDouble(text[k]);
		}
		return r;
	}

	private static String[] read(String fileName) {
		File f = new File(fileName);
		try {
			BufferedReader b = new BufferedReader(new FileReader(f));
			ArrayList<String> text = new ArrayList<>();
			String s = b.readLine();
			while (s != null) {
				text.add(s);
				s = b.readLine();
			}
			b.close();
			return text.toArray(new String[text.size()]);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

}
