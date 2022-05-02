package pl.numerki.backend;

import java.util.function.Function;

public class Approximation {
    private static final int NUM_OF_NODES = 5;

    public static Function<Double, Double> approximate(int degree, Function<Double, Double> f) {
        double lambdaN;
        double[] yFactors = new double[degree + 1];
        for (int i = 0; i <= degree; i++) {
            lambdaN = HermiteQuadrature.integrate(multiplyFunctionsByHermitePolynomial(f, i), NUM_OF_NODES) /
                    (MathOperator.calcPow(2, i) * MathOperator.factorial(i) * Math.sqrt(Math.PI));
            double[] hermiteFactors = HermitePolynomial.calculateFactors(i);
            int hermiteFactorsNum = hermiteFactors.length;
            for (int j = 0; j < hermiteFactorsNum; j++) {
                yFactors[j] += hermiteFactors[j] * lambdaN;
            }
        }
        return Functions.polynomial(yFactors);
    }

    public static double calculateError(Function<Double, Double> original, Function<Double, Double> approximated,
                                        double leftCompartment, double rightCompartment) {
        double error = 0;
        double interval = (rightCompartment - leftCompartment) / 1000.0;
        for (double i = leftCompartment; i <= rightCompartment; i += interval) {
            error += Math.abs(original.apply(i) - approximated.apply(i));
        }
        return error;
    }

    public static Function<Double, Double> multiplyFunctionsByHermitePolynomial(Function<Double, Double> function, int degree) {
        return aDouble -> ( Functions.polynomial(HermitePolynomial.calculateFactors(degree)).apply(aDouble) * function.apply(aDouble));
    }
}
