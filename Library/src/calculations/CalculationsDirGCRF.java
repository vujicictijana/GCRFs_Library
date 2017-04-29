package calculations;

public class CalculationsDirGCRF implements Calculations{

	private int n;
	private double[][] s;
	private double[] r;

	public CalculationsDirGCRF(double[][] s, double[] r) {
		super();
		this.n = s.length;
		this.s = s;
		this.r = r;
	}

	public double[][] l() {

		// rowSum(S)
		double[] rowSum = BasicCalcs.rowSum(s);

		// colSum(S)
		double[] colSum = BasicCalcs.colSum(s);

		// rowSum(S) + colSum(S)
		double[] sumRowCol = BasicCalcs.vectorPlusVector(rowSum, colSum);

		// diag(rowSum(S) + colSum(S))
		double[][] diag = BasicCalcs.diag(sumRowCol);

		// 2S
		double[][] twoS = BasicCalcs.multiplyMatrixByANumber(s, 2);

		// diag(rowSum(S) + colSum(S)) - 2S
		double[][] diagMinusTwoS = BasicCalcs.matrixMinusMatrix(diag, twoS);

		// L = 1/2 * diag(rowSum(S) + colSum(S)) - 2S
		double[][] l = BasicCalcs.multiplyMatrixByANumber(diagMinusTwoS, 0.5);

		return l;
	}

	public double[][] alphaI(double alpha) {
		// Alpha * I (I - identity matrix)
		double[][] identity = BasicCalcs.identityMatrix(s.length);
		return BasicCalcs.multiplyMatrixByANumber(identity, alpha);
	}

	public double[][] betaL(double beta) {
		// Beta * L
		double[][] l = l();
		return BasicCalcs.multiplyMatrixByANumber(l, beta);
	}

	public double[][] q(double alpha, double beta) {
		// Q = alpha*I + beta*L
		double[][] alphaI = alphaI(alpha);
		double[][] betaL = betaL(beta);
		return BasicCalcs.matrixPlusMatrix(alphaI, betaL);
	}

	public double[] b(double alpha) {
		return BasicCalcs.multiplyVectorByANumber(r, alpha);
	}

	public double[] mu(double alpha, double beta) {
		// mu = Q^-1*b
		double[] mu = new double[n];
		double[][] qInverse = BasicCalcs.inverse(q(alpha, beta));
		double[] b = b(alpha);
		mu = BasicCalcs.multiplyMatrixByAColumnVector(qInverse, b);
		return mu;
	}

	public double[] y(double alpha, double beta, double p) {
		double[][] qInverse = BasicCalcs.inverse(q(alpha, beta));
		double[] b = b(alpha);
		double[] y = BasicCalcs.multiplyMatrixByAColumnVector(qInverse, b);
		double[] finalY = new double[y.length];
		for (int i = 0; i < y.length; i++) {
			finalY[i] = y[i] + Math.random() * p;
		}
		return finalY;
	}
	
	public double dervativeAlpha(double alpha, double beta, double[] y) {
		double[] mu =mu(alpha, beta);
		double[][] q = q(alpha, beta);
		double[][] qInverse =BasicCalcs.inverse(q);
		// partial derivative of l with respect to the beta
		// -1/2 * [ (y-mu)^T * (y-mu) + (R-mu)^T [I+Q^-1Q](mu-y)]
		// +1/2 * Tr(Q^-1)

		// yMinusMu = (y-mu)
		double[] yMinusMu = BasicCalcs.vectorMinusVector(y, mu);

		// first = (y-mu)^T * (y-mu)
		double first = BasicCalcs.multiplyTwoVectors(yMinusMu, yMinusMu);

		// res1 = (R-mu)^T
		double[] rMinusMu = BasicCalcs.vectorMinusVector(r, mu);

		// qInverseQ = Q^-1Q

		double[][] qInverseQ = BasicCalcs.multiplyTwoMatrices(qInverse, q);

		// res2 = I+Q^-1Q
		// res2 = I+qInverseQ
		double[][] iPlusQInverseQ = BasicCalcs.matrixPlusMatrix(
				BasicCalcs.identityMatrix(q.length), qInverseQ);

		// muMinusY = (mu-y)
		double[] muMinusY = BasicCalcs.vectorMinusVector(mu, y);

		// second = (R-mu)^T [I+Q^-1Q](mu-y)
		// second = (R-mu)^T * res1
		double[] res1 = BasicCalcs.multiplyMatrixByAColumnVector(
				iPlusQInverseQ, muMinusY);
		double second = BasicCalcs.multiplyTwoVectors(rMinusMu, res1);

		// result1 = -1/2 * [ (y-mu)^T * (y-mu) + (R-mu)^T [I+Q^-1Q](mu-y)]
		// result1 = -1/2 * (first + second)
		double result1 = -(first + second) / 2;

		// trace = Tr(Q^-1)
		// trace = Tr(qInverse)
		double trace = BasicCalcs.trace(qInverse);

		// result2 = 1/2 * Tr(Q^-1)

		// result2 = 1/2 * trace

		double result2 = trace / 2;
		// finalResult = -1/2 * [ (y-mu)^T * (y-mu) + (R-mu)^T [I+Q^-1Q](mu-y)]
		// +1/2 * Tr(Q^-1)
		double finalResult = result1 + result2;

		return finalResult;
	}

	public double dervativeBeta(double alpha, double beta, double[] y) {
		// partial derivative of l with respect to the beta
		// -1/2 * [ y^T*L*y - (-Q^-1*L*mu)^T*Q*y - mu^T*L*y +
		// (-Q^-1*L*mu)^T*Q*mu] +1/2 * Tr(L*Q^-1)
		double[] mu =mu(alpha, beta);
		double[][] q = q(alpha, beta);
		double[][] qInverse =BasicCalcs.inverse(q);
		double[][] l  = l();

		// first = y^T * L * y
		double first = BasicCalcs.multiplyTwoVectors(y,
				BasicCalcs.multiplyMatrixByAColumnVector(l, y));

		// second = (-Q^-1*L*mu)^T*Q*y

		// product1 = Q^-1*L*mu
		// product1 = qInverse * L * mu
		double[] product1 = BasicCalcs.multiplyMatrixByAColumnVector(
				BasicCalcs.multiplyTwoMatrices(qInverse, l), mu);

		// res1 = - Q^-1*L*mu
		// res1 = -1 * product1
		double[] res1 = BasicCalcs.multiplyVectorByANumber(product1, -1);

		// res2= Q*y
		double[] res2 = BasicCalcs.multiplyMatrixByAColumnVector(q, y);

		// second = (-Q^-1*L*mu)^T*Q*y
		// second = res1 * res2
		double second = BasicCalcs.multiplyTwoVectors(res1, res2);

		// third = mu^T*L*y
		double third = BasicCalcs.multiplyTwoVectors(mu,
				BasicCalcs.multiplyMatrixByAColumnVector(l, y));

		// fourth = (-Q^-1*L*mu)^T*Q*mu
		// fourth = res1 * Q*mu
		// res22= Q*mu
		double[] res22 = BasicCalcs.multiplyMatrixByAColumnVector(q, mu);

		double fourth = BasicCalcs.multiplyTwoVectors(res1, res22);

		// result1 = -1/2 * [ y^T*L*y - (-Q^-1*L*mu)^T*Q*y - mu^T*L*y +
		// (-Q^-1*L*mu)^T*Q*mu]
		// result1 = -1/2 * (first + second - third + fourth)
		double result1 = -(first - second - third + fourth) / 2;

		// trace = Tr(L*Q^-1)
		// trace = Tr(L * qInverse)
		
		double[][] matrix = BasicCalcs.multiplyTwoMatrices(l, qInverse);
		double trace = BasicCalcs.trace(matrix);
	

		// result2 = 1/2 * Tr(L*Q^-1)
		// result2 = 1/2 * trace
		double result2 = trace / 2;

		// finalResult = -1/2 * [ y^T*L*y - (-Q^-1*L*mu)^T*Q*y - mu^T*L*y +
		// (-Q^-1*L*mu)^T*Q*mu] +1/2 * Tr(L*Q^-1)
		double finalResult = result1 + result2;

		return finalResult;
	}

}
