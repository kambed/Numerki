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

    public static int[][] readBoard(Path p) throws IOException {
        if (Files.exists(p)) {
            List<String> lines = Files.readAllLines(p);
            int[][] board = new int[lines.size()][lines.size() + 1];
            for (int i = 0; i < lines.size(); i++) {
                String[] rowValues = lines.get(i).split("\\s+");
                for (int j = 0; j < lines.size() + 1; j++) {
                    board[i][j] = Integer.parseInt(rowValues[j]);
                }
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
