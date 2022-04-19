package pl.numerki.backend;

import java.util.function.Function;

public class HermiteQuadrature {
    public static int iterations = 0;

    public static double integrate(
            Function<Double, Double> function,
            double[] zeroPositions, double[] weights
    ) {
        iterations = 0;
        double sum = 0;
        for (int i = 0; i < zeroPositions.length; i++) {
            sum += weights[i] * function.apply(zeroPositions[i]);
            iterations++;
        }
        return sum;
    }

    public static Function<Double, Double> hermiteWeight(Function<Double,Double> function) {
        return aDouble -> Math.exp(-1 * aDouble * aDouble) * function.apply(aDouble);
    }
}
