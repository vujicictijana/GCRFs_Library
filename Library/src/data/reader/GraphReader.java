package data.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class GraphReader {

	public static double[][] readMatrix(String path, int noOfRows, int noOfCols) {
		double[][] matrix = new double[noOfRows][noOfCols];

		String[] text = GraphReader.read(path);

		if (text.length != noOfRows) {
			return null;
		}

		String[] line = null;
		for (int i = 0; i < noOfRows; i++) {
			line = text[i].split(",");
			for (int j = 0; j < noOfCols; j++) {
				matrix[i][j] = Double.parseDouble(line[j]);
			}
		}
		// GraphGenerator.showMatrix(matrix);
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
