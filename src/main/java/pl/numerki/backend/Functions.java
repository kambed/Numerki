package pl.numerki.backend;

import java.util.function.Function;

public class Functions {
    public static Function<Double, Double> polynomial(double[] factors) {
        return aDouble -> {
            double result = 0;
            double powerOfX = 1;
            for (double factor : factors) {
                result += (factor * powerOfX);
                powerOfX *= aDouble;
            }
            return result;
        };
    }

    public static Function<Double, Double> sinFunction() {
        return Math::sin;
    }

    public static Function<Double, Double> exponentialFunction(double a) {
        return aDouble -> Math.pow(a, aDouble);
    }
}
