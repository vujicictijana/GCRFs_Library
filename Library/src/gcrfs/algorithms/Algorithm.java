package gcrfs.algorithms;

public interface Algorithm {
	/**
	 * Returns vector of predicted outputs.
	 */
	public double[] predictOutputs();

	/**
	 * Returns the coefficient of determination (R^2).
	 */
	public double rSquared();
}
