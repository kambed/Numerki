package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class Bisection extends ZeroPosition{
    private static int iteration = 0;
    private static double diff = 0;

    public static double getZeroPosition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon,
            int iterations
    ) {
        double compartmentMiddle = 0;
        for (
            int i = 0;
            abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                && i < iterations
                && leftEndOfCompartment != rightEndOfCompartment;
            i++
        ) {
            compartmentMiddle = (leftEndOfCompartment + rightEndOfCompartment) / 2;

            if (function.apply(compartmentMiddle) == 0) {
                iteration = i;
                if (checkDifferentValuesSign(function, leftEndOfCompartment, compartmentMiddle)) {
                    diff = abs(leftEndOfCompartment-compartmentMiddle);
                } else {
                    diff = abs(rightEndOfCompartment-leftEndOfCompartment);
                }
                return compartmentMiddle;
            }

            if (checkDifferentValuesSign(function, leftEndOfCompartment, compartmentMiddle)) {
                rightEndOfCompartment = compartmentMiddle;
            } else {
                leftEndOfCompartment = compartmentMiddle;
            }
            iteration = i;
            diff = abs(leftEndOfCompartment-rightEndOfCompartment);
        }
        return compartmentMiddle;
    }

    public static int getIteration() {
        return iteration + 1;
    }

    public static double getDiff() {
        return diff;
    }
}
