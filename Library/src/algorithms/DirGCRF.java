package algorithms;

import calculations.BasicCalcs;
import calculations.Calculations;
import calculations.CalculationsDirGCRF;

public class DirGCRF implements Algorithm {

	private double alpha;
	private double beta;
	private double[] expectedY;
	private Calculations calcs;

	public DirGCRF(double alpha, double beta, double[][] s, double[] r, double[] expectedY) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.expectedY = expectedY;
		this.calcs = new CalculationsDirGCRF(s, r);
	}

	public double[] predictOutputs() {
		return calcs.mu(alpha, beta);
	}

	public double rSquared() {
		return BasicCalcs.rSquared(predictOutputs(), expectedY);
	}

}
