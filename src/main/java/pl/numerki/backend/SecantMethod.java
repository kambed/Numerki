package pl.numerki.backend;

import java.util.function.Function;

import static java.lang.Math.abs;

public class SecantMethod extends ZeroPosition {

    public static double getZeroPositionsWithPrecisionCondition(
            Function<Double, Double> function,
            double leftEndOfCompartment,
            double rightEndOfCompartment,
            double epsilon
    ) {
        double leftValue = function.apply(leftEndOfCompartment);
        double rightValue = function.apply(rightEndOfCompartment);
        while (
                abs(rightEndOfCompartment - leftEndOfCompartment) > epsilon
                        && leftEndOfCompartment != rightEndOfCompartment
        ) {
            double secantWithOXCrossPoint = getSecantWithOXCrossPoint(leftValue, rightEndOfCompartment,
                    rightValue, leftEndOfCompartment);
            if(
                    !(
                            (secantWithOXCrossPoint>=leftEndOfCompartment && secantWithOXCrossPoint<=rightEndOfCompartment)
                            || (secantWithOXCrossPoint<=leftEndOfCompartment && secantWithOXCrossPoint>=rightEndOfCompartment)
                    )
            ) {
                return Double.NaN;
            }
            double valueInCrossPoint = function.apply(secantWithOXCrossPoint);
            if (valueInCrossPoint == 0) {
                return secantWithOXCrossPoint;
            }
            leftEndOfCompartment = rightEndOfCompartment;
            leftValue = rightValue;
            rightEndOfCompartment = secantWithOXCrossPoint;
            rightValue = valueInCrossPoint;
        }
        return getSecantWithOXCrossPoint(leftValue, rightEndOfCompartment, rightValue, leftEndOfCompartment);
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
        }
        return getSecantWithOXCrossPoint(leftValue, rightEndOfCompartment, rightValue, leftEndOfCompartment);
    }

    private static double getSecantWithOXCrossPoint(double leftValue, double rightEndOfCompartment,
                                                    double rightValue, double leftEndOfCompartment) {
        return (leftValue * rightEndOfCompartment - rightValue * leftEndOfCompartment) / (leftValue - rightValue);
    }
}
