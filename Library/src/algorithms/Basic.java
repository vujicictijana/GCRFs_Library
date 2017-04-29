package algorithms;

import calculations.BasicCalcs;
import calculations.Calculations;

public class Basic {
	//change calcs
	private double alpha;
	private double beta;
	private double[] expectedY;
	private Calculations calcs;

	public Basic(double alpha, double beta, double[][] s, double[] r, double[] expectedY, Calculations calcs) {
		super();
		this.alpha = alpha;
		this.beta = beta;
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
