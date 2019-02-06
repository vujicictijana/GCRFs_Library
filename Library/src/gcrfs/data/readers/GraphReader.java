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

public class GraphReader {
	/**
	 * Reads graph (adjacency matrix) from the given file.
	 *
	 * @param path
	 *            path to file
	 * @param noOfNodes
	 *            number of elements in the graph
	 * @return the matrix as two dimensional array of double values
	 */
	public static double[][] readGraph(String path, int noOfNodes) {
		double[][] matrix = new double[noOfNodes][noOfNodes];
		String[] text = read(path);
		String[] line;
		int i = 0;
		int j = 0;
		double weigth = 0;
		for (int k = 0; k < text.length; k++) {
			line = text[k].split(",");
			i = Integer.parseInt(line[0]);
			j = Integer.parseInt(line[1]);
			if (i <= 0 || i > noOfNodes || j <= 0 || j > noOfNodes) {
				return null;
			}
			weigth = Double.parseDouble(line[2]);
			matrix[i - 1][j - 1] = weigth;
		}
		return matrix;
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
