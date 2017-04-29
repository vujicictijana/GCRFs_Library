package algorithms;

import calculations.BasicCalcs;
import calculations.Calculations;
import calculations.CalculationsGCRF;

public class GCRF implements Algorithm {

	private double alpha;
	private double beta;
	private double[] expectedY;
	private Calculations calcs;

	public GCRF(double alpha, double beta, double[][] s,
			double[] r, double[] expectedY) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.expectedY = expectedY;
		this.calcs = new CalculationsGCRF(s, r);
	}

	public double[] predictOutputs() {
		return calcs.mu(alpha, beta);
	}

	public double rSquared() {
		return BasicCalcs.rSquared(predictOutputs(), expectedY);
	}

}
