import java.util.Arrays;

public class JordanElimination {
    public static double[] solve(double[][] equation) {
        int numOfEquations = equation[0].length - 1;
        for (int i = 0; i < numOfEquations; i++) {
            for (int j = 0; j < numOfEquations; j++) {
                if (i != j) {
                    double ratio = equation[j][i] / equation[i][i];
                    for (int k = 0; k < numOfEquations + 1; k++) {
                        equation[j][k] -= ratio * equation[i][k];
                    }
                }
            }
        }
        for (int i = 0; i < numOfEquations; i++) {
            double ratio = 1.0 / equation[i][i];
            for (int j = 0; j < numOfEquations + 1; j++) {
                equation[i][j] *= ratio;
            }
        }
        return Arrays.stream(equation).mapToDouble(doubles -> doubles[numOfEquations]).toArray();
    }
}
