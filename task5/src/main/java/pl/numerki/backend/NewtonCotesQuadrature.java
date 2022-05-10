package pl.numerki.backend;

import java.util.function.Function;

public class NewtonCotesQuadrature {
    public static int numberOfSubCompartments;

    public static double integrateFromZeroToInfinity(Function<Double, Double> function, double epsilon) {
        return integrateToInfinity(function, epsilon, 0.5);
    }

    public static double integrateFromMinusInfinityToZero(Function<Double, Double> function, double epsilon) {
        return integrateToInfinity(function, epsilon, -0.5);
    }

    public static double integrateToInfinity(Function<Double, Double> function, double epsilon, double change) {
        double result1, result2 = 0;
        double sum = 0;
        numberOfSubCompartments = 0;
        double leftCompartment = 0, rightCompartment = 0;
        if (change > 0) {
            rightCompartment = change;
        } else {
            leftCompartment = change;
        }
        do {
            do {
                numberOfSubCompartments++;
                result1 = result2;
                result2 = simpson(leftCompartment, rightCompartment, function, numberOfSubCompartments);
            } while (Math.abs(result1 - result2) > 0.01);
            sum += result2;
            rightCompartment += change;
            leftCompartment += change;
        } while (Math.abs(result2) > epsilon);
        return sum;
    }

    public static double integrateFromMinusInfinityToInfinity(Function<Double, Double> function, double epsilon) {
        double fromMinusInfinityToZero = integrateFromMinusInfinityToZero(function, epsilon);
        int numberOfSubCompartments = NewtonCotesQuadrature.numberOfSubCompartments;

        double fromZeroToInfinity = integrateFromZeroToInfinity(function, epsilon);
        NewtonCotesQuadrature.numberOfSubCompartments += numberOfSubCompartments;

        return fromMinusInfinityToZero + fromZeroToInfinity;
    }

    private static double simpson(
            double leftCompartment, double rightCompartment,
            Function<Double, Double> function, int numberOfSubCompartments
    ) {
        double jumpBetweenIntervals = (rightCompartment - leftCompartment) / numberOfSubCompartments;
        return ((rightCompartment - leftCompartment) / (6.0 * numberOfSubCompartments)) * (
                function.apply(leftCompartment) + function.apply(rightCompartment)
                        + 2.0 * sumOfTheBeginningsOfTheSubIntervals(function, leftCompartment, jumpBetweenIntervals, numberOfSubCompartments)
                        + 4.0 * sumOfTheMeansOfTheSubIntervals(function, leftCompartment, jumpBetweenIntervals, numberOfSubCompartments)
        );
    }

    private static double sumOfTheBeginningsOfTheSubIntervals(
            Function<Double, Double> function,
            double leftCompartment, double jumpBetweenIntervals, int numberOfSubCompartments
    ) {
        double sum = 0;
        double compartmentBegin = leftCompartment + jumpBetweenIntervals;
        for (int i = 0; i < numberOfSubCompartments - 1; i++) {
            sum += function.apply(compartmentBegin);
            compartmentBegin += jumpBetweenIntervals;
        }
        return sum;
    }

    private static double sumOfTheMeansOfTheSubIntervals(
            Function<Double, Double> function,
            double leftCompartment, double jumpBetweenIntervals, int numberOfSubCompartments
    ) {
        double sum = 0;
        double compartmentBegin = leftCompartment;
        for (int i = 0; i < numberOfSubCompartments; i++) {
            sum += function.apply(compartmentBegin + (jumpBetweenIntervals / 2.0));
            compartmentBegin += jumpBetweenIntervals;
        }
        return sum;
    }
}
