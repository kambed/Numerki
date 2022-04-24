package pl.numerki.backend;

import java.util.function.Function;

public class NewtonCotesQuadrature {
    public static int numberOfSubCompartments;

    public static double integrate(
            Function<Double, Double> function, double leftCompartment, double rightCompartment, double epsilon
    ) {
        double result1, result2 = 0;
        numberOfSubCompartments = 0;
        do {
            numberOfSubCompartments++;
            result1 = result2;
            result2 = simpson(leftCompartment, rightCompartment, function, numberOfSubCompartments);
        } while (Math.abs(result1 - result2) > epsilon);
        return result2;
    }

    public static double integrateFromZeroToInfinity(Function<Double, Double> function, double epsilon) {
        double result;
        double sum = 0;
        numberOfSubCompartments = 0;
        int rightCompartment = 1;
        int leftCompartment = 0;
        do {
            numberOfSubCompartments++;
            result = simpson(leftCompartment, rightCompartment, function, 1);
            sum += result;
            rightCompartment += 1;
            leftCompartment += 1;
        } while (Math.abs(result) > epsilon);
        return sum;
    }

    public static double integrateFromMinusInfinityToZero(Function<Double, Double> function, double epsilon) {
        double result;
        double sum = 0;
        numberOfSubCompartments = 0;
        int rightCompartment = 0;
        int leftCompartment = -1;
        do {
            numberOfSubCompartments++;
            result = simpson(leftCompartment, rightCompartment, function, 1);
            sum += result;
            rightCompartment -= 1;
            leftCompartment -= 1;
        } while (Math.abs(result) > epsilon);
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
            Function<Double, Double> function, double numberOfSubCompartments
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
            double leftCompartment, double jumpBetweenIntervals, double numberOfSubCompartments
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
            double leftCompartment, double jumpBetweenIntervals, double numberOfSubCompartments
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
