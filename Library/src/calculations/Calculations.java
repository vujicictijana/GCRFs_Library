package calculations;

public interface Calculations {

	public double[][] l();

	public double[][] alphaI(double alpha);

	public double[][] betaL(double beta);

	public double[][] q(double alpha, double beta);

	public double[] b(double alpha);

	public double[] mu(double alpha, double beta);

	public double[] y(double alpha, double beta, double p);

	public double dervativeAlpha(double alpha, double beta, double[] y);

	public double dervativeBeta(double alpha, double beta, double[] y);
}
