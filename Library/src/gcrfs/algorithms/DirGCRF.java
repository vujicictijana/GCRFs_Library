package gcrfs.algorithms;

import gcrfs.calculations.CalculationsDirGCRF;
import gcrfs.data.datasets.Dataset;
import gcrfs.learning.GradientAscent;
import gcrfs.learning.Parameters;
//test
public class DirGCRF extends Basic {

	private GradientAscent learning;

	/**
	 * Class constructor specifying parameters for Gradient descent learning
	 * algorithm and data for DirGCRF.
	 * 
	 */
	public DirGCRF(Parameters parameters, Dataset data) {
		super();
		this.expectedY = data.getY();
		this.calcs = new CalculationsDirGCRF(data.getS(), data.getR());
		this.learning = new GradientAscent(parameters, calcs, expectedY, false, null);
		double[] params = learning.learn();
		this.alpha = params[0];
		this.beta = params[1];
	}

	public double[] predictOutputsForTest(double[][] testS, double[] testR) {
		CalculationsDirGCRF calc = new CalculationsDirGCRF(testS,testR);
		return calc.mu(alpha, beta);
	}
}
