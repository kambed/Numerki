package pl.numerki.backend;

import java.util.function.Function;

public class NewtonCotesQuadrature {
    public static int iterations = 0;
    public static double integrate(
            Function<Double, Double> function, double leftCompartment, double rightCompartment, double epsilon
    ) {
        double result1, result2 = 0;
        iterations = 0;
        do {
            result1 = result2;
            result2 = simpson(leftCompartment, rightCompartment, function);
            iterations++;
        } while (Math.abs(result1 - result2) > epsilon);
        return result2;
    }

    private static double simpson(double leftCompartment, double rightCompartment, Function<Double, Double> function) {
        return ((rightCompartment - leftCompartment) / 3) * (function.apply(leftCompartment)
                    + 4 * function.apply((leftCompartment + rightCompartment) / 2)
                    + function.apply(rightCompartment));
    }
}
