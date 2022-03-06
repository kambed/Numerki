package pl.numerki.backend;

import pl.numerki.backend.exceptions.BisectionException;

import java.util.function.Function;

import static java.lang.Math.abs;

public class Bisection {
    public static double getZeroPositionsWithPrecisionCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon
    ) throws Exception {
        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                        && leftEndOfCompartment != rightEndOfCompartment
        ) {
            double compartmentMiddle = (leftEndOfCompartment + rightEndOfCompartment) / 2;

            if (abs(function.apply(compartmentMiddle)) < epsilon) {
                return compartmentMiddle;
            }

            if (function.apply(leftEndOfCompartment) * function.apply(compartmentMiddle) < 0) {
                rightEndOfCompartment = compartmentMiddle;
            } else {
                leftEndOfCompartment = compartmentMiddle;
            }
        }
        throw new BisectionException();
    }

    public static double getZeroPositionsWithIterationCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            int numberOfIterations
    ) throws Exception {
        for (int i = 0; i < numberOfIterations; i++) {
            double compartmentMiddle = (leftEndOfCompartment + rightEndOfCompartment) / 2;
            if (function.apply(compartmentMiddle) == 0) {
                return compartmentMiddle;
            }
            if (function.apply(leftEndOfCompartment) * function.apply(compartmentMiddle) < 0) {
                rightEndOfCompartment = compartmentMiddle;
            } else {
                leftEndOfCompartment = compartmentMiddle;
            }
        }
        return (leftEndOfCompartment + rightEndOfCompartment) / 2;
    }
}
