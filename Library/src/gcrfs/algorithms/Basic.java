package gcrfs.algorithms;

import gcrfs.calculations.BasicCalcs;
import gcrfs.calculations.Calculations;
import gcrfs.data.datasets.Dataset;
import gcrfs.learning.LearningAlgorithm;

public class Basic implements Algorithm {
	protected double alpha;
	protected double beta;
	protected double[] expectedY;
	protected Calculations calcs;

	public Basic() {
		super();
	}

	/**
	 * Class constructor specifying learning algorithm (object of class that
	 * implements LearningAlgorithm interface), calculation rules (object of
	 * class that implements Calculations interface) and data (object of Dataset
	 * class).
	 * 
	 */
	public Basic(LearningAlgorithm l, Calculations calcs, Dataset data) {
		super();
		double[] array = l.learn();
		this.alpha = array[0];
		this.beta = array[1];
		this.expectedY = data.getY();
		this.calcs = calcs;
	}

	public double[] predictOutputs() {
		return calcs.mu(alpha, beta);
	}

	public double rSquared() {
		return BasicCalcs.rSquared(predictOutputs(), expectedY);
	}

	public double rSquaredForTest(double[] predictedY, double[] expectedY) {
		return BasicCalcs.rSquared(predictedY, expectedY);
	}
	
	public double[] getParameters(){
		return new double[] {alpha, beta};
	}

}
