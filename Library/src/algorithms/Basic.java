package algorithms;

import calculations.BasicCalcs;
import calculations.Calculations;
import learning.LearningAlgorithm;

public class Basic {
	//change calcs
	private double alpha;
	private double beta;
	private double[] expectedY;
	private Calculations calcs;

	public Basic(LearningAlgorithm l, double[][] s, double[] r, double[] expectedY, Calculations calcs) {
		super();
		double[] array = l.learn();
		this.alpha = array[0];
		this.beta =  array[1];
		this.expectedY = expectedY;
		this.calcs = calcs;
	}

	public double[] predictOutputs() {
		return calcs.mu(alpha, beta);
	}

	public double rSquared() {
		return BasicCalcs.rSquared(predictOutputs(), expectedY);
	}
}
