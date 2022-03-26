public class JordanElimination {
    private static final double PRECISION = 0.0001;

    public static double[] solve(double[][] equation) throws IndefiniteException, ConflictException {
        int numOfEquations = equation[0].length - 1;
        for (int i = 0; i < numOfEquations; i++) {
            // Replace rows if 0 is found on the diagonal
            if (equation[i][i] == 0) {
                for (int j = i; j < numOfEquations; j++) {
                    if (equation[j][i] != 0) {
                        for (int k = 0; k < numOfEquations + 1; k++) {
                            double temp = equation[j][k];
                            equation[j][k] = equation[i][k];
                            equation[i][k] = temp;
                        }
                    }
                }
            }
            // Zeroing the column
            for (int j = 0; j < numOfEquations; j++) {
                if (i != j) {
                    double ratio = equation[j][i] / equation[i][i];
                    boolean row0 = true;
                    for (int k = 0; k < numOfEquations + 1; k++) {
                        equation[j][k] -= ratio * equation[i][k];
                        if (row0 && k != numOfEquations && Math.abs(equation[j][k]) > PRECISION) {
                            row0 = false;
                        }
                    }

                    if (row0) {
                        if (Math.abs(equation[j][numOfEquations]) < PRECISION) {
                            throw new IndefiniteException("Układ nieoznaczony!");
                        } else {
                            throw new ConflictException("Układ sprzeczny!");
                        }
                    }
                }
            }
        }

        double[] result = new double[numOfEquations];
        for (int i = 0; i < numOfEquations; i++) {
            result[i] = equation[i][numOfEquations] / equation[i][i];
        }
        return result;
    }
}
