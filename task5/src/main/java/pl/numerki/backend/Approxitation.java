package pl.numerki.backend;

import java.util.function.Function;

public class Approxitation {
    public static Function<Double, Double> approximate(int degree, Function<Double, Double> f) {
        double yn;
        double[] yFactors = new double[degree + 1];
        for (int i = 0; i <= degree; i++) {
            yn = HermiteQuadrature.integrate(multiplyFunctions(
                    f, Functions.polynomial(HermitePolynomial.calculateFactors(i))), 5) /
                    (MathOperator.calcPow(2, i) * MathOperator.factorial(i) * Math.sqrt(Math.PI));
            int hermiteFactorsNum = HermitePolynomial.calculateFactors(i).length;
            double[] hermiteFactors = HermitePolynomial.calculateFactors(i);
            for (int j = 0; j < hermiteFactorsNum; j++) {
                yFactors[j] += hermiteFactors[j] * yn;
            }
        }
        return Functions.polynomial(yFactors);
    }

    public static Function<Double, Double> multiplyFunctions(Function<Double, Double> function, Function<Double, Double> function2) {
        return aDouble -> (function2.apply(aDouble) * function.apply(aDouble));
    }
}
