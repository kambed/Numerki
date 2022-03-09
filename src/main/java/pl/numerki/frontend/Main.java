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
        System.out.print("Podaj liczbe zlozen: ");
        Scanner s = new Scanner(System.in);
        int numOfAssemblies = s.nextInt();

        Function<Double, Double>[] function = new Function[numOfAssemblies];
        for (int j = 0; j < numOfAssemblies; j++) {
            System.out.println(getMenu());
            int choose = s.nextInt();
            switch (choose) {
                case 1 -> function[j] = Functions.sinFunction();
                case 2 -> {
                    System.out.print("Podaj a: ");
                    double a = s.nextDouble();
                    function[j] = Functions.exponentialFunction(a);
                }
                case 3 -> {
                    System.out.print("Podaj a: ");
                    double a = s.nextDouble();
                    System.out.print("Podaj b: ");
                    double b = s.nextDouble();
                    System.out.print("Podaj c: ");
                    double c = s.nextDouble();
                    function[j] = Functions.squareFunction(a, b, c);
                }
                default -> {
                    System.out.println("Wybrano nie prawidlowa opcje.");
                    return;
                }
            }
        }
        Function<Double, Double> assembledFunction = assemble(function);

        System.out.println("Podaj przedzial testowy");
        double leftCompartment = s.nextDouble();
        double rightCompartment = s.nextDouble();

        if (!ZeroPosition.checkDifferentValuesSign(assembledFunction, leftCompartment, rightCompartment)) {
            System.out.println("Wartości funckcji w predziałach końcowych muszą mieć różne znaki.");
            return;
        }

        System.out.println("Wybierz warunek zakonczenia: \n" + getEnding());
        int choose = s.nextInt();
        double bisectionResult, secantResult;
        switch (choose) {
            case 1 -> {
                System.out.print("Podaj liczbe iteracji: ");
                int iterations = s.nextInt();
                bisectionResult = Bisection.getZeroPositionsWithIterationCondition(
                        assembledFunction, leftCompartment, rightCompartment, iterations);
                secantResult = SecantMethod.getZeroPositionsWithIterationCondition(
                        assembledFunction, leftCompartment, rightCompartment, iterations);
                System.out.println("Bisekcja: " + bisectionResult);
                System.out.println("Metoda siecznych: " + secantResult);
            }
            case 2 -> {
                System.out.print("Podaj dokladnosc: ");
                double epsilon = s.nextDouble();
                bisectionResult = Bisection.getZeroPositionsWithPrecisionCondition(
                        assembledFunction, leftCompartment, rightCompartment, epsilon);
                secantResult = SecantMethod.getZeroPositionsWithPrecisionCondition(
                        assembledFunction, leftCompartment, rightCompartment, epsilon);
                System.out.println("Bisekcja: " + bisectionResult);
                System.out.println("Metoda siecznych: " + secantResult);
            }
            default -> throw new Exception();
        }

        try {
            ChartUtilities.saveChartAsPNG(
                    new File("chart.png"),
                    ChartGenerator.generatePlot(assembledFunction, leftCompartment, rightCompartment, bisectionResult, secantResult),
                    600, 300
            );
        } catch (IOException e) {
            System.out.println("Wyspapił problem przy generowaniu wykresu.");
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
