import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOperator {
    private static int rows;
    private static int columns;

    public static double[][] readBoard(Path p) throws IOException {
        if (Files.exists(p)) {
            List<String> lines = Files.readAllLines(p);
            double[][] board = new double[lines.size()][lines.size() + 1];
            int i = 0;
            int j = 0;
            for (String s : lines) {
                for (String arg : s.split("\\s+")) {
                    board[i][j] = Double.parseDouble(arg);
                    j++;
                }
                j = 0;
                i++;
            }
            return board;
        }
        return null;
    }

    public static int getRows() {
        return rows;
    }

    public static int getColumns() {
        return columns;
    }
}
