package pl.numerki.backend;

public class HermitePolynomial {
    public static int[] calculateFactors(int degree) {
        int[] factors = new int[degree / 2 + 1];
        for (int i = 0; i <= degree / 2; i++) {
            factors[i] = (calcPow(-1, i) * factorial(degree) * calcPow(2, degree - 2 * i)) /
                    (factorial(i) * factorial(degree - 2 * i));
        }
        return factors;
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static int calcPow(int num, int n) {
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= num;
        }
        return result;
    }
}
