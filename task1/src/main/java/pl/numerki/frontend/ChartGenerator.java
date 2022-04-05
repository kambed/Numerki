package pl.numerki.frontend;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.numerki.backend.ZeroPosition;

import java.awt.*;
import java.util.function.Function;

public class ChartGenerator {
    public static JFreeChart generatePlot(
            Function<Double, Double> function, double leftCompartment, double rightCompartment,
            double bisectionZeroPosition, double secantZeroPosition
    ) {
        XYSeries functionSeries = new XYSeries("Funkcja");
        double unit = (rightCompartment - leftCompartment) / 1000;
        double minValue = function.apply(leftCompartment);
        double maxValue = function.apply(leftCompartment);
        for (double i = leftCompartment; i < rightCompartment; i += unit) {
            double value = function.apply(i);
            functionSeries.add(i, value);
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
        }

        XYSeries zeroPositionSeries = new XYSeries("Miejsca zerowe");
        if (!Double.isNaN(bisectionZeroPosition))
            zeroPositionSeries.add(bisectionZeroPosition, function.apply(bisectionZeroPosition));
        if (!Double.isNaN(secantZeroPosition))
            zeroPositionSeries.add(secantZeroPosition, function.apply(secantZeroPosition));

        XYSeries xAxis = new XYSeries("oś X");
        xAxis.add(leftCompartment,0);
        xAxis.add(rightCompartment,0);

        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        seriesCollection.addSeries(functionSeries);
        seriesCollection.addSeries(zeroPositionSeries);
        seriesCollection.addSeries(xAxis);

        if(!ZeroPosition.checkDifferentValuesSign(function, leftCompartment, rightCompartment)) {
            xAxis.add(0,0);
            XYSeries yAxis = new XYSeries("oś Y");
            yAxis.add(0, minValue);
            yAxis.add(0, maxValue);
            yAxis.add(0,0);
            seriesCollection.addSeries(yAxis);
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres funckji", "X", "Y", seriesCollection,
                PlotOrientation.VERTICAL, true, true, false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        changeVisibility(renderer, 0, true);
        changeVisibility(renderer, 1, false);
        formatAxis(renderer,2);
        formatAxis(renderer,3);

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
