package pl.numerki.backend;

import java.util.function.Function;

public class Functions {
    public static Function<Double, Double> polynomial(double[] factors) {
        return aDouble -> {
            double result = factors[factors.length-1];
            for (int i = factors.length - 2; i >= 0; i--) {
                result *= aDouble;
                result += factors[i];
            }
            return result;
        };
    }

    public static Function<Double, Double> sinFunction() {
        return Math::sin;
    }

    public static Function<Double, Double> exponentialFunction(double a) {
        return aDouble -> {
            if (aDouble % 1 == 0) {
                double result = 1;
                for (int i = 0; i < Math.abs(aDouble); i++) {
                    result *= a;
                }
                return result;
            } else {
                return Math.pow(a, aDouble);
            }
        };
    }
}
