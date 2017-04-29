package calculations;

public class CalculationsGCRF implements Calculations{

	private double[][] s;
	private double[] r;

	public CalculationsGCRF(double[][] s, double[] r) {
		super();
		this.s = s;
		this.r = r;
	}

	public double[][] q(double alpha, double beta) {
		// Q = 2*Alpha*I + 2*Beta*L
		double[][] alphaI = alphaI(alpha);
		double[][] betaL = betaL(beta);
		return BasicCalcs.matrixPlusMatrix(
				BasicCalcs.multiplyMatrixByANumber(alphaI, 2),
				BasicCalcs.multiplyMatrixByANumber(betaL, 2));
	}

	public double[][] alphaI(double alpha) {
		// Alpha * I (I - identity matrix)
		double[][] identity = BasicCalcs.identityMatrix(s.length);
		return BasicCalcs.multiplyMatrixByANumber(identity, alpha);
	}

	public double[][] l() {
		// Laplacian matrix
		// L = degreeMatrix - adjacencyMatrix
		return BasicCalcs.matrixMinusMatrix(BasicCalcs.degreeMatrix(s), s);
	}

	public double[][] betaL(double beta) {
		// Beta * L (L- Laplacian matrix)
		// L = degreeMatrix - adjacencyMatrix
		return BasicCalcs.multiplyMatrixByANumber(l(), beta);
	}

	public double[] mu(double alpha, double beta) {
		// mu = Q^-1*b
		return BasicCalcs.multiplyMatrixByAColumnVector(
				BasicCalcs.inverse(q(alpha, beta)), b(alpha));
	}

	public double[] b(double alpha) {
		// b=R*alpha
		return BasicCalcs.multiplyVectorByANumber(r, alpha);
	}

	public double[] y(double alpha, double beta, double p) {
		double[] y = mu(alpha, beta);
		double[] finalY = new double[y.length];
		for (int i = 0; i < y.length; i++) {
			finalY[i] = y[i] + Math.random() * p;
		}
		return finalY;
	}

	public double dervativeAlpha(double alpha, double beta, double[] y) {
		// partial derivative with respect to the beta
		// -1/2 * [ y^Ty + 2R^T(mu-y)-mu^Tmu] +1/2 * Tr(Q^-1)
		double[] mu =mu(alpha, beta);
		double[][] q = q(alpha, beta);

		// yTy = y^Ty
		double yTy = BasicCalcs.multiplyTwoVectors(y, y);

		// muMinusY = (mu-y)
		double[] muMinusY = BasicCalcs.vectorMinusVector(mu, y);

		// res = 2R^T(mu-y)
		double res = BasicCalcs.multiplyTwoVectors(
				BasicCalcs.multiplyVectorByANumber(r, 2), muMinusY);
		// muTmu = mu^Tmu
		double muTmu = BasicCalcs.multiplyTwoVectors(mu, mu);

		// result1 = -1/2 * [ y^Ty + 2R^T(mu-y)-mu^Tmu]
		// result1 = - [yTy+res-muTmu]/2
		double result1 = -(yTy + res - muTmu) / 2;

		double trace = BasicCalcs.trace(BasicCalcs.inverse(q));
		// result2 = 1/2 * Tr(Q^-1)
		// result2 = trace/2
		double result2 = trace / 2;

		return result1 + result2;
	}
	


	public double dervativeBeta(double alpha, double beta, double[] y) {
		// partial derivative respect to the beta
		// -1/2 * (y^T*L*y - mu^T*L*mu) + 1/2 * Tr(L*Q^-1)
		double[] mu = mu(alpha, beta);
		double[][] l = l();
		double[][] q = q(alpha, beta);

		// yTLy = y^T*L*y
		double yTLy = BasicCalcs.multiplyTwoVectors(
				BasicCalcs.multiplyMatrixByAColumnVector(l, y), y);

		// muTLmu = mu^T*L*mu
		double muTLmu = BasicCalcs.multiplyTwoVectors(
				BasicCalcs.multiplyMatrixByAColumnVector(l, mu), mu);

		// result1 = -1/2 * (y^T*L*y - mu^T*L*mu)
		// result1 = - [yTLy - muTLmu]/2
		double result1 = -(yTLy - muTLmu) / 2;

		// trace = Tr(L*Q^-1)
		double trace = BasicCalcs.trace(BasicCalcs.multiplyTwoMatrices(l,
				BasicCalcs.inverse(q)));

		// result2 = 1/2 * Tr(L*Q^-1)
		// result2 = trace/2
		double result2 = trace / 2;

		return result1 + result2;
	}
}
