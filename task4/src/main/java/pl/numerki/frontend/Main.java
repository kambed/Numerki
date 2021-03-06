package pl.numerki.frontend;

import pl.numerki.backend.Functions;
import pl.numerki.backend.HermiteQuadrature;
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

        System.out.print("Podaj dokładność dla metody Newtona-Cotesa: ");
        double epsilon = s.nextDouble();

        double leftCompartment, rightCompartment;

        System.out.print("Czy wykonujemy całkowanie kwadraturą Gausa z wielomianem Hermite'a? (y/n) ");
        String hermite = s.next();
        switch (hermite) {
            case "y", "Y" -> {
                for (int i = 2; i < 6; i++) {
                    System.out.println(
                            "Kwadratura Gausa z wielomianem Hermite'a dla " + i + " węzłów: " +
                                    "\n    wynik: " +
                                    HermiteQuadrature.integrate(assembledFunction, i) +
                                    "\n    iteracje: " + HermiteQuadrature.iterations
                    );
                }

                System.out.println(
                        "Kwadratura Newtona-Cotesa: " +
                                "\n    wynik: " +
                                NewtonCotesQuadrature.integrateFromMinusInfinityToInfinity(
                                        HermiteQuadrature.hermiteWeight(assembledFunction), epsilon) +
                                "\n    liczba podziałów: " + NewtonCotesQuadrature.numberOfSubCompartments
                );
            }
            case "n", "N" -> {
                System.out.println("Podaj przedział testowy");
                System.out.print("Początek: ");
                leftCompartment = s.nextDouble();
                System.out.print("Koniec: ");
                rightCompartment = s.nextDouble();

                System.out.println(
                        "Kwadratura Newtona-Cotesa: " +
                                "\n    wynik: " +
                                NewtonCotesQuadrature.integrate(assembledFunction, leftCompartment, rightCompartment, epsilon) +
                                "\n    liczba podziałów: " + NewtonCotesQuadrature.numberOfSubCompartments
                );
            }
            default -> System.out.println("Wprowadzono nieprawidłową wartość");
        }
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
