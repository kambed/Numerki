package pl.numerki.frontend;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pl.numerki.backend.Bisection;
import pl.numerki.backend.SecantMethod;
import pl.numerki.backend.ZeroPosition;

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
            if (!ZeroPosition.checkDifferentValuesSign(function, from, to)) {
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
                        "Metoda bisekcji: " + resultBisection +
                        "\nMetoda siecznych: " + resultSecant,
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
            default -> throw new Exception("Wybrano złą funkcję");
        };
    }

    public void squareFunctionChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        setParametersVisibility(true, true, true);
    }

    public void sinusChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        setParametersVisibility(false, false, false);
    }

    public void exponentialChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        setParametersVisibility(true, false, false);
    }

    public void complexFunctionChosen(ActionEvent actionEvent) {
        setChosenFunction(actionEvent);
        setParametersVisibility(true, true, true);
    }

    private void setChosenFunction(ActionEvent actionEvent) {
        function.setText(((MenuItem) actionEvent.getSource()).getText());
        chosenFunction = ((MenuItem) actionEvent.getSource()).getText();
    }

    public void showIterations(ActionEvent actionEvent) {
        setEndConditionVisibility(false, true);
        endCondition.setText(((MenuItem) actionEvent.getSource()).getText());
    }

    public void showPrecision(ActionEvent actionEvent) {
        setEndConditionVisibility(true,false);
        endCondition.setText(((MenuItem) actionEvent.getSource()).getText());
    }

    private void setParametersVisibility(boolean a, boolean b, boolean c) {
        labelA.setVisible(a);
        labelB.setVisible(b);
        labelC.setVisible(c);
        this.a.setVisible(a);
        this.b.setVisible(b);
        this.c.setVisible(c);
        if (!a) {
            this.a.setText("");
        }
        if (!b) {
            this.b.setText("");
        }
        if (!c) {
            this.c.setText("");
        }
    }

    private void  setEndConditionVisibility (boolean precision, boolean iterations) {
        precisionLabel.setVisible(precision);
        this.precision.setVisible(precision);
        this.iterations.setVisible(iterations);
        iterationsLabel.setVisible(iterations);
        if (!precision) {
            this.precision.setText("");
        }
        if(!iterations) {
            this.iterations.setText("");
        }
    }
}