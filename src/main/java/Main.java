import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Podaj plik: ");
        Scanner scanner = new Scanner(System.in);
        String pathString = scanner.next();
        Path board = Paths.get("src/main/resources/" + pathString);
        System.out.println(board.toAbsolutePath());
        double[][] equation = new double[0][0];
        try {
             equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            double[] solution = JordanElimination.solve(equation);
            System.out.println(Arrays.toString(solution));
        } catch (IndefiniteException ie) {
            System.out.println("Nieoznaczony");
        } catch (ConflictException ce) {
            System.out.println("Sprzeczny");
        }
    }
}
