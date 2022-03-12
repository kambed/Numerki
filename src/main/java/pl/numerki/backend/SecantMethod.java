package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class SecantMethod extends ZeroPosition {

    public static double getZeroPosition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon,
            int iterations
    ) {
        double leftValue = function.apply(leftEndOfCompartment);
        double rightValue = function.apply(rightEndOfCompartment);
        int i = 0;
        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                        && i < iterations
                        && leftEndOfCompartment != rightEndOfCompartment
        ) {
            double secantWithOXCrossPoint = getSecantWithOXCrossPoint(leftValue, rightEndOfCompartment,
                    rightValue, leftEndOfCompartment);
            double valueInCrossPoint = function.apply(secantWithOXCrossPoint);
            if (valueInCrossPoint == 0) {
                return secantWithOXCrossPoint;
            }
            leftEndOfCompartment = rightEndOfCompartment;
            leftValue = rightValue;
            rightEndOfCompartment = secantWithOXCrossPoint;
            rightValue = valueInCrossPoint;
            i++;
        }
        return getSecantWithOXCrossPoint(leftValue, rightEndOfCompartment, rightValue, leftEndOfCompartment);
    }

    private static double getSecantWithOXCrossPoint(double leftValue, double rightEndOfCompartment,
                                                    double rightValue, double leftEndOfCompartment) {
        return (leftValue * rightEndOfCompartment - rightValue * leftEndOfCompartment) / (leftValue - rightValue);
    }
}
