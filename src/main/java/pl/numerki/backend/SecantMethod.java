package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class SecantMethod extends ZeroPosition {
    private static int iteration = 0;
    private static double diff = 0;

    public static double getZeroPosition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon,
            int iterations
    ) {
        double leftValue = function.apply(leftEndOfCompartment);
        double rightValue = function.apply(rightEndOfCompartment);
        double secantWithOXCrossPoint = 0;
        for (
            int i = 0;
            abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                && i < iterations
                && leftEndOfCompartment != rightEndOfCompartment;
            i++
        ) {
            secantWithOXCrossPoint = getSecantWithOXCrossPoint(leftValue, rightEndOfCompartment,
                    rightValue, leftEndOfCompartment);
            double valueInCrossPoint = function.apply(secantWithOXCrossPoint);
            if (valueInCrossPoint == 0) {
                iteration = i;
                diff = abs(secantWithOXCrossPoint-rightEndOfCompartment);
                return secantWithOXCrossPoint;
            }
            leftEndOfCompartment = rightEndOfCompartment;
            leftValue = rightValue;
            rightEndOfCompartment = secantWithOXCrossPoint;
            System.out.println(rightEndOfCompartment);
            rightValue = valueInCrossPoint;
            iteration = i;
            diff = abs(leftEndOfCompartment-rightEndOfCompartment);
        }
        return secantWithOXCrossPoint;
    }

    private static double getSecantWithOXCrossPoint(double leftValue, double rightEndOfCompartment,
                                                    double rightValue, double leftEndOfCompartment) {
        return (leftValue * rightEndOfCompartment - rightValue * leftEndOfCompartment) / (leftValue - rightValue);
    }

    public static int getIteration() {
        return iteration + 1;
    }

    public static double getDiff() {
        return diff;
    }
}
