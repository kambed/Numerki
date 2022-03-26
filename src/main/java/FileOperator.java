import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileOperator {

    public static double[][] readBoard(Path p) throws IOException {
        if (Files.exists(p)) {
            List<String> lines = Files.readAllLines(p);
            double[][] board = new double[lines.size()][lines.size() + 1];
            for (int i = 0; i < lines.size(); i++) {
                String[] rowValues = lines.get(i).split("\\s+");
                for (int j = 0; j < lines.size() + 1; j++) {
                    board[i][j] = Double.parseDouble(rowValues[j]);
                }
            }
            return board;
        }
        return null;
    }
}
