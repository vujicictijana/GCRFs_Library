package gcrfs.learning;

public interface LearningAlgorithm {
	
	/**
	 * Returns an array of parameters that are learned. 
	 */
	public double[] learn();
}
