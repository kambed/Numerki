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
        double[][] equation = getEquation(1);
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1, solution[0], 0.001);
        assertEquals(2, solution[1], 0.001);
        assertEquals(3, solution[2], 0.001);
    }

    @Test
    @DisplayName("Equation 2")
    void equation2() {
        double[][] equation = getEquation(2);
        assertThrows(IndefiniteException.class, () -> JordanElimination.solve(equation));
    }

    @Test
    @DisplayName("Equation 3")
    void equation3() {
        double[][] equation = getEquation(3);
        assertThrows(ConflictException.class, () -> JordanElimination.solve(equation));
    }

    @Test
    @DisplayName("Equation 4")
    void equation4() throws ConflictException, IndefiniteException {
        double[][] equation = getEquation(4);
        double[] solution = JordanElimination.solve(equation);
        assertEquals(2, solution[0], 0.001);
        assertEquals(-3, solution[1], 0.001);
        assertEquals(1.5, solution[2], 0.001);
        assertEquals(0.5, solution[3], 0.001);
    }

    @Test
    @DisplayName("Equation 5")
    void equation5() {
        double[][] equation = getEquation(5);
        assertThrows(ConflictException.class, () -> JordanElimination.solve(equation));
    }

    @Test
    @DisplayName("Equation 6")
    void equation6() throws ConflictException, IndefiniteException {
        double[][] equation = getEquation(6);
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1, solution[0], 0.001);
        assertEquals(3, solution[1], 0.001);
        assertEquals(-4, solution[2], 0.001);
        assertEquals(5, solution[3], 0.001);
    }

    @Test
    @DisplayName("Equation 7")
    void equation7() throws ConflictException, IndefiniteException {
        double[][] equation = getEquation(7);
        double[] solution = JordanElimination.solve(equation);
        assertEquals(7, solution[0], 0.001);
        assertEquals(5, solution[1], 0.001);
        assertEquals(3, solution[2], 0.001);
    }

    @Test
    @DisplayName("Equation 8")
    void equation8() throws ConflictException, IndefiniteException {
        double[][] equation = getEquation(8);
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1, solution[0], 0.001);
        assertEquals(2, solution[1], 0.001);
        assertEquals(3, solution[2], 0.001);
    }

    @Test
    @DisplayName("Equation 9")
    void equation9() {
        double[][] equation = getEquation(9);
        assertThrows(IndefiniteException.class, () -> JordanElimination.solve(equation));
    }

    @Test
    @DisplayName("Equation 10")
    void equation10() throws ConflictException, IndefiniteException {
        double[][] equation = getEquation(10);
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1, solution[0], 0.001);
        assertEquals(1, solution[1], 0.001);
        assertEquals(1, solution[2], 0.001);
    }

    private double[][] getEquation(int i) {
        Path board = Paths.get("src/test/resources/equation" + i + ".txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return equation;
    }
}