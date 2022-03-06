package pl.numerki.backend;

import pl.numerki.backend.exceptions.SecantException;

import java.util.function.Function;

import static java.lang.Math.abs;

public class SecantMethod {

    public static double getZeroPositionsWithPrecisionCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon
    ) throws Exception {
        double leftValue = function.apply(leftEndOfCompartment);
        double rightValue = function.apply(rightEndOfCompartment);
        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                        && leftEndOfCompartment != rightEndOfCompartment
        ) {
            double secantWithOXCrossPoint = (leftValue * rightEndOfCompartment - rightValue * leftEndOfCompartment) / (leftValue - rightValue);
            double valueInCrossPoint = function.apply(secantWithOXCrossPoint);
            if (abs(valueInCrossPoint) < epsilon) {
                return secantWithOXCrossPoint;
            }
            leftEndOfCompartment = rightEndOfCompartment;
            leftValue = rightValue;
            rightEndOfCompartment = secantWithOXCrossPoint;
            rightValue = valueInCrossPoint;
        }
        throw new SecantException();
    }

    public static double getZeroPositionsWithIterationCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            int iterations
    ) {
        double leftValue = function.apply(leftEndOfCompartment);
        double rightValue = function.apply(rightEndOfCompartment);
        for (int i = 0; i < iterations && leftEndOfCompartment != rightEndOfCompartment; i++) {
            double secantWithOXCrossPoint = (leftValue * rightEndOfCompartment - rightValue * leftEndOfCompartment) / (leftValue - rightValue);
            double valueInCrossPoint = function.apply(secantWithOXCrossPoint);
            if (valueInCrossPoint == 0) {
                return secantWithOXCrossPoint;
            }
            leftEndOfCompartment = rightEndOfCompartment;
            leftValue = rightValue;
            rightEndOfCompartment = secantWithOXCrossPoint;
            rightValue = valueInCrossPoint;
        }
        return (leftValue * rightEndOfCompartment - rightValue * leftEndOfCompartment) / (leftValue - rightValue);
    }
}
