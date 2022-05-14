package pl.numerki.frontend;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.function.Function;

public class ChartGenerator {
    public static JFreeChart generatePlot(
            Function<Double, Double> function, Function<Double, Double> approximated,
            double leftCompartment, double rightCompartment
    ) {
        XYSeries functionSeries = new XYSeries("Funkcja aproksymowana");
        XYSeries approximatedSeries = new XYSeries("Funkcja aproksymująca");
        double unit = (rightCompartment - leftCompartment) / 1000;
        double minValue = function.apply(leftCompartment);
        double maxValue = function.apply(leftCompartment);
        for (double i = leftCompartment; i < rightCompartment; i += unit) {
            double value = function.apply(i);
            double valueApproximated = approximated.apply(i);
            approximatedSeries.add(i, valueApproximated);
            functionSeries.add(i, value);
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
            if (valueApproximated < minValue) {
                minValue = valueApproximated;
            }
            if (valueApproximated > maxValue) {
                maxValue = valueApproximated;
            }
        }

        XYSeries xAxis = new XYSeries("oś X");
        xAxis.add(leftCompartment, 0);
        xAxis.add(rightCompartment, 0);

        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        seriesCollection.addSeries(functionSeries);
        seriesCollection.addSeries(approximatedSeries);
        seriesCollection.addSeries(xAxis);

        if (leftCompartment * rightCompartment < 0) {
            xAxis.add(0, 0);
            XYSeries yAxis = new XYSeries("oś Y");
            yAxis.add(0, minValue);
            yAxis.add(0, maxValue);
            yAxis.add(0, 0);
            seriesCollection.addSeries(yAxis);
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres funkcji", "X", "Y", seriesCollection,
                PlotOrientation.VERTICAL, true, true, false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        changeVisibility(renderer, 0, true);
        changeVisibility(renderer, 1, true);
        formatAxis(renderer, 2);
        formatAxis(renderer, 3);

        plot.setRenderer(renderer);

        return chart;
    }

    private static void formatAxis(XYLineAndShapeRenderer renderer, int series) {
        changeVisibility(renderer, series, true);
        renderer.setSeriesPaint(series, new Color(0x00, 0x00, 0x00));
        renderer.setSeriesStroke(series, new BasicStroke(0.5f));
    }

    private static void changeVisibility(XYLineAndShapeRenderer renderer, int series, boolean displayLine) {
        renderer.setSeriesLinesVisible(series, displayLine);
        renderer.setSeriesShapesVisible(series, !displayLine);
    }
}