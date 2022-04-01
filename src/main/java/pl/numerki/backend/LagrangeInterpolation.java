package pl.numerki.backend;

import java.util.Arrays;
import java.util.function.Function;

public class LagrangeInterpolation {
    public static Function interpolate(double[] x, double[] y) {
        double[] factors = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double xValues = 1;
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    xValues *= x[i] - x[j];
                }
            }
            factors[i] = y[i] / xValues;
        }
        double[] interpolatedFunctionFactors = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double[] polynomialFactors = {1};
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    double[] multiplyFactors = {-x[j], 1};
                    polynomialFactors = functionMultiPly(polynomialFactors, multiplyFactors);
                }
            }
            for (int j = 0; j < polynomialFactors.length; j++) {
                interpolatedFunctionFactors[j] += polynomialFactors[j] * factors[i];
            }
        }
        return Functions.polynomial(interpolatedFunctionFactors);
    }

    private static double[] functionMultiPly(double[] factorsA, double[] factorsB) {
        double[] result = new double[factorsA.length + 1];
        result[0] = factorsB[0] * factorsA[0];
        for (int i = 1; i < factorsA.length; i++) {
            result[i] = factorsA[i - 1] + factorsA[i] * factorsB[0];
        }
        result[factorsA.length] = 1;
        return result;
    }
}
