package pl.numerki.frontend;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pl.numerki.backend.Bisection;
import pl.numerki.backend.Functions;

public class MainFormController {

    public static final String MAIN_FORM_RESOURCE = "MainForm.fxml";
    public static final String MAIN_FORM_TITLE = "Zadanie 1";
    public TextField iterations;
    public TextField precision;
    public TextField from;
    public TextField to;

    public Menu function;

    public Label labelA;
    public TextField a;

    public Label labelB;
    public TextField b;

    public Label labelC;
    public TextField c;
    public Label iterationsLabel;
    public Label precisionLabel;
    public Menu endCondition;

    private String chosenFunction;

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

    public void getZeroPositions() {
        double result = 0;
        try {
            if(!this.precision.getText().isEmpty()) {
                result = getPrecisionResult();
            } else if (!this.iterations.getText().isEmpty()) {
                result = getIterationResult();
            }
        } catch (Exception e) {
            AlertBox.alertShow(
                "Miejsce zerowe na zadanym przedziale",
                    "Nie odnaleziono miejsca zeroewgo dla zadanych parmetrów",
                    Alert.AlertType.INFORMATION
            );
            return;
        }

        AlertBox.alertShow(
                "Miejsce zerowe na zadanym przedziale",
                "Miejsce zerowe na zadanym przedziale wynosi " + String.valueOf(result),
                Alert.AlertType.INFORMATION
        );
    }

    private double getIterationResult() throws Exception {
        double a = Double.parseDouble(this.a.getText());
        double b = Double.parseDouble(this.b.getText());
        double c = Double.parseDouble(this.c.getText());
        double from = Double.parseDouble(this.from.getText());
        double to = Double.parseDouble(this.to.getText());
        int iteration = Integer.parseInt(this.iterations.getText());
        return switch (chosenFunction) {
            case "ax^2+bx+c" -> Bisection.getZeroPositionsWithIterationCondition(
                    Functions.squareFunction(a, b, c),
                    from, to, iteration
            );
            case "sin(x)" -> Bisection.getZeroPositionsWithIterationCondition(
                    Functions.sinFunction(),
                    from, to, iteration
            );
            case "a^x" -> Bisection.getZeroPositionsWithIterationCondition(
                    Functions.exponentialFunction(a),
                    from, to, iteration
            );
            case "sin(ax^2+bx+c)" -> Bisection.getZeroPositionsWithIterationCondition(
                    Functions.complexFunction(a, b, c),
                    from, to, iteration
            );
            default -> throw new Exception();
        };
    }

    private double getPrecisionResult() throws Exception {
        double a = Double.parseDouble(this.a.getText());
        double b = Double.parseDouble(this.b.getText());
        double c = Double.parseDouble(this.c.getText());
        double from = Double.parseDouble(this.from.getText());
        double to = Double.parseDouble(this.to.getText());
        double precision = Double.parseDouble(this.precision.getText());
        return switch (chosenFunction) {
            case "ax^2+bx+c" -> Bisection.getZeroPositionsWithPrecisionCondition(
                    Functions.squareFunction(a, b, c),
                    from, to, precision
            );
            case "sin(x)" -> Bisection.getZeroPositionsWithPrecisionCondition(
                    Functions.sinFunction(),
                    from, to, precision
            );
            case "a^x" -> Bisection.getZeroPositionsWithPrecisionCondition(
                    Functions.exponentialFunction(a),
                    from, to, precision
            );
            case "sin(ax^2+bx+c)" -> Bisection.getZeroPositionsWithPrecisionCondition(
                    Functions.complexFunction(a, b, c),
                    from, to, precision
            );
            default -> throw new Exception();
        };
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