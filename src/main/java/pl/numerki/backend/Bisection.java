package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class Bisection {
    public static double getZeroPositionsWithPrecisionCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon
    ) throws Exception {
        if (function.apply(leftEndOfCompartment) * function.apply(rightEndOfCompartment) >= 0) {
            throw new Exception();
        }

        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                        && leftEndOfCompartment != rightEndOfCompartment
        ) {
            double compartmentMiddle = (leftEndOfCompartment + rightEndOfCompartment) / 2;

            if (function.apply(compartmentMiddle) + epsilon > 0 && function.apply(compartmentMiddle) - epsilon < 0) {
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

    public static double getZeroPositionsWithIterationCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            int numberOfIterations
    ) throws Exception {
        if (function.apply(leftEndOfCompartment) * function.apply(rightEndOfCompartment) == 0) {
            throw new Exception();
        }

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
