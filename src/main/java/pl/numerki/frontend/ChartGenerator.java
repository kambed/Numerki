package pl.numerki.frontend;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.function.Function;

public class ChartGenerator {
    public static JFreeChart generatePlot(
            Function<Double, Double> function, double leftCompartment, double rightCompartment,
            double bisectionZeroPosition, double secantZeroPosition
    ) {
        XYSeries series = new XYSeries(0);
        double unit = (rightCompartment - leftCompartment) / 1000;
        for (double i = leftCompartment; i < rightCompartment; i += unit) {
            series.add(i, function.apply(i));
        }

        XYSeries compartmentsAndZeroPosition = new XYSeries(1);
        compartmentsAndZeroPosition.add(bisectionZeroPosition, 0);
        compartmentsAndZeroPosition.add(secantZeroPosition, 0);

        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        seriesCollection.addSeries(series);
        seriesCollection.addSeries(compartmentsAndZeroPosition);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Chart", "x", "y", seriesCollection,
                PlotOrientation.VERTICAL, false, true, false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        plot.setRenderer(renderer);

        return chart;
    }
}
