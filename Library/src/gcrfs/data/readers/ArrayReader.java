/** This file is part of GCRF GUI TOOL.

    GCRF GUI TOOL is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    GCRF GUI TOOL is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with GCRF GUI TOOL.  If not, see <https://www.gnu.org/licenses/>.*/

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
