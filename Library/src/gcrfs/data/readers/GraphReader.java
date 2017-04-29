package gcrfs.data.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphReader {

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
