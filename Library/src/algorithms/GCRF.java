package algorithms;

import calculations.BasicCalcs;
import calculations.Calculations;
import calculations.CalculationsGCRF;
import learning.GradientDescent;

public class GCRF implements Algorithm {

	private double alpha;
	private double beta;
	private double[] expectedY;
	private Calculations calcs;
	private GradientDescent learning; 
	
	public GCRF(double startAlpha, double startBeta, double[][] s, double[] r, double[] expectedY, int maxIter, double learningRate) {
		super();
		this.expectedY = expectedY;
		this.calcs = new CalculationsGCRF(s, r);
		this.learning = new GradientDescent(startAlpha, startBeta, learningRate, calcs, expectedY, maxIter, false, null);
		double[] params = learning.learn();
		this.alpha= params[0];
		this.beta  = params[1];
	}

	public double[] predictOutputs() {
		return calcs.mu(alpha, beta);
	}

	public double rSquared() {
		return BasicCalcs.rSquared(predictOutputs(), expectedY);
	}

}
