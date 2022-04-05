package pl.numerki.backend;

import java.util.function.Function;

public class ZeroPosition {
    public static boolean checkDifferentValuesSign(
            Function<Double, Double> function,
            double value1,
            double value2
    ){
        return function.apply(value1) * function.apply(value2) < 0;
    }
}
