import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    @Test
    @DisplayName("Equation 1")
    void equation1() throws ConflictException, IndefiniteException {
        Path board = Paths.get("src/test/resources/equation1.txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1,solution[0]);
        assertEquals(2,solution[1]);
        assertEquals(3,solution[2]);
    }

    @Test
    @DisplayName("Equation 2")
    void equation2() {
        Path board = Paths.get("src/test/resources/equation2.txt");
        try {
            double[][] equation = FileOperator.readBoard(board);
            assertThrows(IndefiniteException.class,() -> JordanElimination.solve(equation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Equation 3")
    void equation3() {
        Path board = Paths.get("src/test/resources/equation3.txt");
        try {
            double[][] equation = FileOperator.readBoard(board);
            assertThrows(ConflictException.class,() -> JordanElimination.solve(equation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}