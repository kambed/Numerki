package pl.numerki.backend;
import java.util.function.Function;

public class HermiteQuadrature {
    public static int iterations = 0;

    public static double integrate(Function<Double, Double> function, int numberOfNodes) {
        iterations = 0;
        double sum = 0;
        double[] zeroPositions = getZeroPositions(numberOfNodes);
        double[] weights = getWeights(numberOfNodes);
        for (int i = 0; i < zeroPositions.length; i++) {
            sum += weights[i] * function.apply(zeroPositions[i]);
            iterations++;
        }
        return sum;
    }

    private static double[] getWeights(int n) {
        return switch (n) {
            case 2 -> new double[]{0.886227, 0.886227};
            case 3 -> new double[]{0.295409, 1.181636, 0.295409};
            case 4 -> new double[]{0.081313, 0.804914, 0.804914, 0.081313};
            case 5 -> new double[]{0.019953, 0.393619, 0.945309, 0.393619, 0.019953};
            default -> throw new IllegalArgumentException();
        };
    }

    private static double[] getZeroPositions(int n) {
        return switch (n) {
            case 2 -> new double[]{-0.707107, 0.707107};
            case 3 -> new double[]{-1.224745, 0, 1.224745};
            case 4 -> new double[]{-1.650680, -0.534648, 0.534648, 1.650680};
            case 5 -> new double[]{-2.020183, -0.958572, 0, 0.958572, 2.020183};
            default -> throw new IllegalArgumentException();
        };
    }

    public static Function<Double, Double> hermiteWeight(Function<Double, Double> function) {
        return aDouble -> (Math.exp(-1 * aDouble * aDouble) * function.apply(aDouble));
    }
}
