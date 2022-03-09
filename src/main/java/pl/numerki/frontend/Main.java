package pl.numerki.frontend;

import pl.numerki.backend.Bisection;
import pl.numerki.backend.Functions;
import pl.numerki.backend.SecantMethod;
import pl.numerki.backend.ZeroPosition;

import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        System.out.println("Podaj liczbe zlozen");
        Scanner s = new Scanner(System.in);
        int numOfAssemblies = s.nextInt();

        Function<Double, Double>[] function = new Function[numOfAssemblies];
        for (int j = 0; j < numOfAssemblies; j++) {
            System.out.println(getMenu());
            int choose = s.nextInt();
            switch (choose) {
                case 1 -> function[j] = Functions.sinFunction();
                case 2 -> {
                    System.out.println("Podaj a");
                    double a = s.nextDouble();
                    function[j] = Functions.exponentialFunction(a);
                }
                case 3 -> {
                    System.out.println("Podaj a");
                    double a = s.nextDouble();
                    System.out.println("Podaj b");
                    double b = s.nextDouble();
                    System.out.println("Podaj c");
                    double c = s.nextDouble();
                    function[j] = Functions.squareFunction(a, b, c);
                }
                default -> {
                    System.out.println("Wybrano nie prawidlowa opcje");
                    return;
                }
            }
        }
        Function<Double, Double> assembledFunction = assemble(function);

        System.out.println("Podaj przedzial testowy");
        double leftCompartment = s.nextDouble();
        double rightCompartment = s.nextDouble();

        if (!ZeroPosition.checkDifferentValuesSign(assembledFunction, leftCompartment, rightCompartment)) {
            System.out.println("Wartości funckcji w predziałach końcowych muszą mieć różne znaki");
            return;
        }

        System.out.println("Wybierz warunek zakonczenia \n" + getEnding());
        int choose = s.nextInt();
        switch (choose) {
            case 1 -> {
                System.out.println("Podaj liczbe iteracji");
                int iterations = s.nextInt();
                System.out.println("Bisekcja: " + Bisection.getZeroPositionsWithIterationCondition(
                        assembledFunction, leftCompartment, rightCompartment, iterations));
                System.out.println("Metoda siecznych: " + SecantMethod.getZeroPositionsWithIterationCondition(
                        assembledFunction, leftCompartment, rightCompartment, iterations));
            }
            case 2 -> {
                System.out.println("Podaj dokladnosc");
                double epsilon = s.nextDouble();
                System.out.println("Bisekcja: " + Bisection.getZeroPositionsWithPrecisionCondition(
                        assembledFunction, leftCompartment, rightCompartment, epsilon));
                System.out.println("Metoda siecznych: " + SecantMethod.getZeroPositionsWithPrecisionCondition(
                        assembledFunction, leftCompartment, rightCompartment, epsilon));
            }
        }
    }

    private static String getMenu() {
        return """
                Funckje:\s
                1 - sin(x)\s
                2 - a^x\s
                3 - ax^2+bx+c""";
    }

    private static String getEnding() {
        return """
                Funckje:\s
                1 - ilosc iteracji\s
                2 - dokladnosc""";
    }

    private static Function<Double, Double> assemble(Function<Double, Double>[] array) {
        return aDouble -> {
            double result = aDouble;
            for (Function<Double, Double> doubleDoubleFunction : array) {
                result = doubleDoubleFunction.apply(result);
            }
            return result;
        };
    }
}
