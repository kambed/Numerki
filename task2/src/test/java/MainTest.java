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
        assertEquals(1,solution[0],0.001);
        assertEquals(2,solution[1],0.001);
        assertEquals(3,solution[2],0.001);
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

    @Test
    @DisplayName("Equation 4")
    void equation4() throws ConflictException, IndefiniteException {
        Path board = Paths.get("src/test/resources/equation4.txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] solution = JordanElimination.solve(equation);
        assertEquals(2,solution[0],0.001);
        assertEquals(-3,solution[1],0.001);
        assertEquals(1.5,solution[2],0.001);
        assertEquals(0.5,solution[3],0.001);
    }

    @Test
    @DisplayName("Equation 5")
    void equation5() {
        Path board = Paths.get("src/test/resources/equation5.txt");
        try {
            double[][] equation = FileOperator.readBoard(board);
            assertThrows(ConflictException.class,() -> JordanElimination.solve(equation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Equation 6")
    void equation6() throws ConflictException, IndefiniteException {
        Path board = Paths.get("src/test/resources/equation6.txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1,solution[0],0.001);
        assertEquals(3,solution[1],0.001);
        assertEquals(-4,solution[2],0.001);
        assertEquals(5,solution[3],0.001);
    }

    @Test
    @DisplayName("Equation 7")
    void equation7() throws ConflictException, IndefiniteException {
        Path board = Paths.get("src/test/resources/equation7.txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] solution = JordanElimination.solve(equation);
        assertEquals(7,solution[0],0.001);
        assertEquals(5,solution[1],0.001);
        assertEquals(3,solution[2],0.001);
    }

    @Test
    @DisplayName("Equation 8")
    void equation8() throws ConflictException, IndefiniteException {
        Path board = Paths.get("src/test/resources/equation8.txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1,solution[0],0.001);
        assertEquals(2,solution[1],0.001);
        assertEquals(3,solution[2],0.001);
    }

    @Test
    @DisplayName("Equation 9")
    void equation9() {
        Path board = Paths.get("src/test/resources/equation9.txt");
        try {
            double[][] equation = FileOperator.readBoard(board);
            assertThrows(IndefiniteException.class,() -> JordanElimination.solve(equation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Equation 10")
    void equation10() throws ConflictException, IndefiniteException {
        Path board = Paths.get("src/test/resources/equation10.txt");
        double[][] equation = new double[0][0];
        try {
            equation = FileOperator.readBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] solution = JordanElimination.solve(equation);
        assertEquals(1,solution[0],0.001);
        assertEquals(1,solution[1],0.001);
        assertEquals(1,solution[2],0.001);
    }

}