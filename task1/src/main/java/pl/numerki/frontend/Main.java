package pl.numerki.frontend;

import org.jfree.chart.ChartUtilities;
import pl.numerki.backend.Bisection;
import pl.numerki.backend.Functions;
import pl.numerki.backend.SecantMethod;
import pl.numerki.backend.ZeroPosition;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("Podaj liczbę złożeń: ");
        Scanner s = new Scanner(System.in);
        int numOfAssemblies = s.nextInt();

        Function<Double, Double>[] function = new Function[numOfAssemblies];
        for (int j = 0; j < numOfAssemblies; j++) {
            System.out.println(getMenu());
            System.out.print("Wybór: ");
            int choose = s.nextInt();
            switch (choose) {
                case 1 -> function[j] = Functions.sinFunction();
                case 2 -> {
                    System.out.print("Podaj a: ");
                    double a = s.nextDouble();
                    if (a < 0) {
                        System.out.println("Podstawa funkcji wykładniczej musi być liczbą dodatnią.");
                        return;
                    }
                    function[j] = Functions.exponentialFunction(a);
                }
                case 3 -> {
                    System.out.print("Podaj stopień wielomianu, który chcesz wprowadzić: ");
                    int grade = s.nextInt();
                    if (grade < 0) {
                        System.out.println("Wybrano nieprawidłowy stopień wielomianu.");
                        return;
                    }
                    double[] factors = new double[grade + 1];
                    for (int i = 0; i < grade + 1; i++) {
                        System.out.print("Podaj współczynnik przy " + i + " potędze wielomianu: ");
                        factors[i] = s.nextDouble();
                    }
                    function[j] = Functions.polynomial(factors);
                }
                default -> {
                    System.out.println("Wybrano nie prawidłową opcję.");
                    return;
                }
            }
        }
        Function<Double, Double> assembledFunction = assemble(function);

        System.out.println("Podaj przedział testowy");
        System.out.print("Początek: ");
        double leftCompartment = s.nextDouble();
        System.out.print("Koniec: ");
        double rightCompartment = s.nextDouble();

        if (!ZeroPosition.checkDifferentValuesSign(assembledFunction, leftCompartment, rightCompartment)) {
            System.out.println("Wartości funkcji w przedziałach końcowych muszą mieć różne znaki.");
            return;
        }

        System.out.println("Wybierz warunek zakończenia: \n" + getEnding());
        System.out.print("Wybór: ");
        int choose = s.nextInt();
        double bisectionResult, secantResult;
        int iterations = Integer.MAX_VALUE;
        double epsilon = -1;
        switch (choose) {
            case 1 -> {
                System.out.print("Podaj liczbę iteracji: ");
                iterations = s.nextInt();
            }
            case 2 -> {
                System.out.print("Podaj dokładność: ");
                epsilon = s.nextDouble();
            }
            default -> {
                System.out.println("Wybrano nie prawidłową opcję.");
                return;
            }
        }
        bisectionResult = Bisection.getZeroPosition(
                assembledFunction, leftCompartment, rightCompartment, epsilon, iterations);
        secantResult = SecantMethod.getZeroPosition(
                assembledFunction, leftCompartment, rightCompartment, epsilon, iterations);
        System.out.println("Bisekcja: " + bisectionResult);
        System.out.println("Bisekcja iteracje: " + Bisection.getIteration());
        System.out.println("Bisekcja epsilon: " + Bisection.getDiff());
        System.out.println("Metoda siecznych: " + secantResult);
        System.out.println("Metoda siecznych iteracje: " + SecantMethod.getIteration());
        System.out.println("Metoda siecznych epsilon: " + SecantMethod.getDiff());

        try {
            ChartUtilities.saveChartAsPNG(
                    new File("chart.png"),
                    ChartGenerator.generatePlot(
                            assembledFunction,
                            leftCompartment, rightCompartment,
                            bisectionResult, secantResult
                    ),
                    600, 600
            );
        } catch (IOException e) {
            System.out.println("Wystapił problem przy generowaniu wykresu.");
        }
    }

    private static String getMenu() {
        return """
                Funckje:\s
                1 - sin(x)\s
                2 - a^x\s
                3 - wielomian (a + bx + cx^2 + ...)""";
    }

    private static String getEnding() {
        return """
                Funckje:\s
                1 - ilosc iteracji\s
                2 - dokładność""";
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
