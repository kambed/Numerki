package pl.numerki.backend;

public class HermitePolynomial {
    public static double[] calculateFactors(int degree) {
        double[] factors = new double[degree + 1];
        for (int i = 0; i <= degree / 2; i++) {
            factors[degree - 2 * i] = 1.0 * (MathOperator.calcPow(-1, i) *
                    MathOperator.factorial(degree) * MathOperator.calcPow(2, degree - 2 * i)) /
                    (MathOperator.factorial(i) * MathOperator.factorial(degree - 2 * i));
        }
        return factors;
    }
}
