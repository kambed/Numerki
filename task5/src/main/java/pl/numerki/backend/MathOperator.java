package pl.numerki.backend;

public class MathOperator {
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
