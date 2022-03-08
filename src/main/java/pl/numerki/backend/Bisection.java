package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class Bisection extends ZeroPosition{
    public static double getZeroPositionsWithPrecisionCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon
    ) {
        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
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
        }
        return (leftEndOfCompartment + rightEndOfCompartment) / 2;
    }

    public static double getZeroPositionsWithIterationCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            int numberOfIterations
    ) {
        for (int i = 0; i < numberOfIterations; i++) {
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
