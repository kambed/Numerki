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
        int i = 0;
        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                        && i < iterations
                        && leftEndOfCompartment != rightEndOfCompartment
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
            i++;
        }
        return (leftEndOfCompartment + rightEndOfCompartment) / 2;
    }

}
