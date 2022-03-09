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
        String functionString = "";
        for (int j = 0; j < numOfAssemblies; j++) {
            System.out.println(getMenu());
            System.out.print("Wybór: ");
            int choose = s.nextInt();
            switch (choose) {
                case 1 -> {
                    function[j] = Functions.sinFunction();

                    if (!functionString.isEmpty())
                        functionString = "sin(x)".replace("x", functionString);
                    else functionString = "sin(x)";
                }
                case 2 -> {
                    System.out.print("Podaj a: ");
                    double a = s.nextDouble();
                    function[j] = Functions.exponentialFunction(a);

                    if (!functionString.isEmpty())
                        functionString = (a + "^x").replace("x", "(" + functionString + ")");
                    else functionString = a + "^x";
                }
                case 3 -> {
                    System.out.print("Podaj a: ");
                    double a = s.nextDouble();
                    System.out.print("Podaj b: ");
                    double b = s.nextDouble();
                    System.out.print("Podaj c: ");
                    double c = s.nextDouble();
                    function[j] = Functions.squareFunction(a, b, c);
                    if (!functionString.isEmpty())
                        functionString = (a + "x^2+" + b + "x+" + c).replace("x", "(" + functionString + ")");
                    else functionString = a + "x^2+" + b + "x+" + c;
                }
                default -> {
                    System.out.println("Wybrano nie prawidlową opcję.");
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
            System.out.println("Wartości funckcji w predziałach końcowych muszą mieć różne znaki.");
            return;
        }

        System.out.println("Wybierz warunek zakonczenia: \n" + getEnding());
        System.out.print("Wybór: ");
        int choose = s.nextInt();
        double bisectionResult, secantResult;
        switch (choose) {
            case 1 -> {
                System.out.print("Podaj liczbę iteracji: ");
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
            default -> {
                System.out.println("Wybrano nie prawidlową opcję.");
                return;
            }
        }

        try {
            ChartUtilities.saveChartAsPNG(
                    new File("chart.png"),
                    ChartGenerator.generatePlot(
                            assembledFunction,
                            leftCompartment, rightCompartment,
                            bisectionResult, secantResult,
                            functionString
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
