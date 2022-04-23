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
            double jumpBetweenIntervals = (rightCompartment - leftCompartment) / numberOfSubCompartments;
            result1 = result2;
            result2 = simpson(leftCompartment, rightCompartment, function, jumpBetweenIntervals);
        } while (Math.abs(result1 - result2) > epsilon);
        return result2;
    }

    private static double simpson(
            double leftCompartment, double rightCompartment,
            Function<Double, Double> function, double jumpBetweenIntervals
    ) {
        return ((rightCompartment - leftCompartment) / (6 * numberOfSubCompartments)) * (
                function.apply(leftCompartment) + function.apply(rightCompartment)
                        + 2 * sumOfTheBeginningsOfTheSubIntervals(function, leftCompartment, jumpBetweenIntervals)
                        + 4 * sumOfTheMeansOfTheSubIntervals(function, leftCompartment, jumpBetweenIntervals)
        );
    }

    private static double sumOfTheBeginningsOfTheSubIntervals(
            Function<Double, Double> function,
            double leftCompartment, double jumpBetweenIntervals
    ) {
        double sum = 0;
        double compartmentBegin = leftCompartment + jumpBetweenIntervals;
        for (int i = 1; i < numberOfSubCompartments - 1; i++) {
            sum += function.apply(compartmentBegin);
            compartmentBegin += jumpBetweenIntervals;
        }
        return sum;
    }

    private static double sumOfTheMeansOfTheSubIntervals(
            Function<Double, Double> function,
            double leftCompartment, double jumpBetweenIntervals
    ) {
        double sum = 0;
        double compartmentBegin = leftCompartment;
        for (int i = 1; i < numberOfSubCompartments - 1; i++) {
            sum += function.apply(compartmentBegin + (jumpBetweenIntervals / 2.0));
            compartmentBegin += jumpBetweenIntervals;
        }
        return sum;
    }
}
