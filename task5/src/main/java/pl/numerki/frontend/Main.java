package pl.numerki.frontend;

import org.jfree.chart.ChartUtilities;
import pl.numerki.backend.Functions;
import pl.numerki.backend.HermitePolynomial;
import pl.numerki.backend.NewtonCotesQuadrature;

import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
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
                case 4 -> function[j] = Functions.absoluteFunction();
                default -> {
                    System.out.println("Wybrano nie prawidłową opcję.");
                    return;
                }
            }
        }
        Function<Double, Double> assembledFunction = assemble(function);

        double leftCompartment, rightCompartment;

        System.out.println("Podaj przedział aproksymacji");
        System.out.print("Początek: ");
        leftCompartment = s.nextDouble();
        System.out.print("Koniec: ");
        rightCompartment = s.nextDouble();

        System.out.print("Podaj stopień wielomianu aproksymującego: ");
        int polynomialDegree = s.nextInt();

        System.out.print("Podaj dokładność dla metody Newtona-Cotesa: ");
        double epsilon = s.nextDouble();

        System.out.println(
                "Kwadratura Newtona-Cotesa: " +
                        "\n    wynik: " +
                        NewtonCotesQuadrature.integrate(assembledFunction, leftCompartment, rightCompartment, epsilon) +
                        "\n    liczba podziałów: " + NewtonCotesQuadrature.numberOfSubCompartments
        );

//        try {
//            ChartUtilities.saveChartAsPNG(
//                    new File("chart.png"),
//                    ChartGenerator.generatePlot(
//                            assembledFunction,
//                            interpolatedFunction,
//                            leftCompartment,
//                            rightCompartment,
//                            nodesX
//                    ),
//                    600, 600
//            );
//        } catch (IOException e) {
//            System.out.println("Wystapił problem przy generowaniu wykresu.");
//        }
    }

    private static String getMenu() {
        return """
                Funckje:\s
                1 - sin(x)\s
                2 - a^x\s
                3 - wielomian (a + bx + cx^2 + ...)\s
                4 - |x|""";
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