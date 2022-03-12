package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class Bisection extends ZeroPosition{
    public static double getZeroPosition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon,
            int iterations
    ) {
        for (
            int i = 0;
            abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                && i < iterations
                && leftEndOfCompartment != rightEndOfCompartment;
            i++
        ) {
            double compartmentMiddle = (leftEndOfCompartment + rightEndOfCompartment) / 2;

            if (function.apply(compartmentMiddle) == 0) {
                return compartmentMiddle;
            }

            if (checkDifferentValuesSign(function, leftEndOfCompartment, compartmentMiddle)) {
                rightEndOfCompartment = compartmentMiddle;
            } else {
                leftEndOfCompartment = compartmentMiddle;
            }
        }
        return (leftEndOfCompartment + rightEndOfCompartment) / 2;
    }

}
