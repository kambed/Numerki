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
        int[][] equation = new int[0][0];
        try {
             equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(equation[0]));
    }
}
