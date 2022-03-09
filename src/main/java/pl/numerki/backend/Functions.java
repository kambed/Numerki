package pl.numerki.backend;

import java.util.function.Function;

public class Functions {
    public static Function<Double, Double> squareFunction(double a, double b, double c) {
        return aDouble -> a * aDouble * aDouble + b * aDouble + c;
    }

    public static Function<Double, Double> sinFunction() {
        return Math::sin;
    }

    public static Function<Double, Double> exponentialFunction(double a) {
        return aDouble -> Math.pow(a, aDouble);
    }
}
