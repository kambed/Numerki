package pl.numerki.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileOperator {

    public static double[] readNodes(Path p) throws IOException {
        if (Files.exists(p)) {
            return null;
        }
        List<String> lines = Files.readAllLines(p);
        String[] rowValues = lines.get(0).split("\\s+");
        double[] nodes = new double[rowValues.length];
        for (int i = 0; i < rowValues.length; i++) {
            nodes[i] = Double.parseDouble(rowValues[i]);
        }
        return nodes;
    }
}
