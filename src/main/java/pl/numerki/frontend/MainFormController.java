package pl.numerki.frontend;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pl.numerki.backend.Bisection;
import pl.numerki.backend.SecantMethod;
import pl.numerki.backend.exceptions.BisectionException;
import pl.numerki.backend.exceptions.SecantException;

import java.util.function.Function;

import static pl.numerki.backend.Functions.*;

public class MainFormController {

    public static final String MAIN_FORM_RESOURCE = "MainForm.fxml";
    public static final String MAIN_FORM_TITLE = "Zadanie 1";

    public Menu function;
    public Menu endCondition;

    public Label iterationsLabel;
    public Label precisionLabel;
    public TextField iterations;
    public TextField precision;

    public TextField from;
    public TextField to;

    public Label labelA;
    public TextField a;

    public Label labelB;
    public TextField b;

    public Label labelC;
    public TextField c;

    private String chosenFunction;

    public void getZeroPositions() {
        boolean bisectionException = false;
        boolean secantException = false;
        double resultBisection = 0;
        double resultSecant = 0;
        try {
            Function<Double, Double> function = getFunction();
            double from, to;
            try {
                from = Double.parseDouble(this.from.getText());
                to = Double.parseDouble(this.to.getText());
            } catch (NumberFormatException e) {
                throw new Exception("Nie podano liczb");
            }
            // Check different signs on end of compartments
            if (function.apply(from) * function.apply(to) >= 0) {
                throw new Exception("Wartości funckcji w predziałach końcowych muszą mieć różne znaki");
            }
            if (!this.precision.getText().isEmpty()) {
                double precision = Double.parseDouble(this.precision.getText());
                resultBisection = Bisection.getZeroPositionsWithPrecisionCondition(function, from, to, precision);
                resultSecant = SecantMethod.getZeroPositionsWithPrecisionCondition(function, from, to, precision);
            } else if (!this.iterations.getText().isEmpty()) {
                int iterations = Integer.parseInt(this.iterations.getText());
                resultBisection = Bisection.getZeroPositionsWithIterationCondition(function, from, to, iterations);
                resultSecant = SecantMethod.getZeroPositionsWithIterationCondition(function, from, to, iterations);
            }
        } catch (BisectionException e) {
            bisectionException = true;
        } catch (SecantException e) {
            secantException = true;
        } catch (Exception e) {
            AlertBox.alertShow(
                    "Miejsce zerowe na zadanym przedziale",
                    e.getMessage(),
                    Alert.AlertType.INFORMATION
            );
            return;
        }

        AlertBox.alertShow(
                "Miejsce zerowe na zadanym przedziale",
                "Miejsce zerowe na zadanym przedziale wynosi: \n" +
                        "Metoda bisekcji: " + (bisectionException ? "NanN" : resultBisection) +
                        "\nMetoda siecznych: " + (secantException ? "NaN" : resultSecant),
                Alert.AlertType.INFORMATION
        );
    }

    private Function<Double, Double> getFunction() throws Exception {
        double a, b, c;
        try {
            a = Double.parseDouble(this.a.getText());
            b = Double.parseDouble(this.b.getText());
            c = Double.parseDouble(this.c.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Nie podano liczb");
        }
        return switch (chosenFunction) {
            case "ax^2+bx+c" -> squareFunction(a, b, c);
            case "sin(x)" -> sinFunction();
            case "a^x" -> exponentialFunction(a);
            case "sin(ax^2+bx+c)" -> complexFunction(a, b, c);
        };
    }

    public void squareFunctionChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        labelA.setVisible(true);
        labelB.setVisible(true);
        labelC.setVisible(true);
        a.setVisible(true);
        b.setVisible(true);
        c.setVisible(true);
    }

    public void sinusChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        labelA.setVisible(false);
        labelB.setVisible(false);
        labelC.setVisible(false);
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
    }

    public void exponentialChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        labelA.setVisible(true);
        labelB.setVisible(false);
        labelC.setVisible(false);
        a.setVisible(true);
        b.setVisible(false);
        c.setVisible(false);
    }

    public void complexFunctionChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        labelA.setVisible(true);
        labelB.setVisible(true);
        labelC.setVisible(true);
        a.setVisible(true);
        b.setVisible(true);
        c.setVisible(true);
    }

    private void setChosenFunction(ActionEvent actionEvent) {
        function.setText(((MenuItem) actionEvent.getSource()).getText());
        chosenFunction = ((MenuItem) actionEvent.getSource()).getText();
    }

    public void showIterations(ActionEvent actionEvent) {
        iterations.setVisible(true);
        iterationsLabel.setVisible(true);
        precisionLabel.setVisible(false);
        precision.setVisible(false);
        precision.setText("");
        endCondition.setText(((MenuItem) actionEvent.getSource()).getText());
    }

    public void showPrecision(ActionEvent actionEvent) {
        precisionLabel.setVisible(true);
        precision.setVisible(true);
        iterations.setVisible(false);
        iterationsLabel.setVisible(false);
        iterations.setText("");
        endCondition.setText(((MenuItem) actionEvent.getSource()).getText());
    }
}