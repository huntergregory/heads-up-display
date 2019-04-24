package hud.plotting;

import javafx.scene.chart.*;

/**
 * This class is used to construct plots to dynamically display game variables
 * @author Carter Gay
 */
public class PlotBuilder {

    private DataTracker<Double> myX;
    private DataTracker<Double>[] myY;
    private double myWidth;
    private double myHeight;
    private NumberAxis myXAxis;
    private NumberAxis myYAxis;


    /**
     * PlotBuilder constructor that takes in an x feature and any number of y features
     * @param width
     * @param height
     * @param x
     * @param y
     */
    public PlotBuilder( double width, double height, DataTracker<Double> x, DataTracker<Double> ... y) {
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myXAxis = new NumberAxis();
        myXAxis.setLabel(x.getDataName());
        myYAxis = new NumberAxis();
    }

    /**
     * Creates the scatter plot of the passed in x and y features with appropriate titles/labels
     * @return
     */
    public XYChart<Number,Number> createPlot(String plotType) {
        XYChart myPlot;
        if (plotType == "Scatter") {
            myPlot = new ScatterChart<>(myXAxis, myYAxis);
        }
        else if (plotType == "Line") {
            myPlot = new LineChart<>(myXAxis, myYAxis);
        }
        else  {
            myPlot = new AreaChart<>(myXAxis, myYAxis);
        }
        myPlot.setMaxWidth(myWidth);
        myPlot.setMaxHeight(myHeight);
        if (myY.length != 0)
            plotData(myYAxis, myPlot);
        else
            myPlot.setTitle("Please select y");
        return myPlot;
    }

    private void plotData(NumberAxis yAxis, XYChart<Number, Number> myPlot) {
        String yString = myY[0].getDataName();
        for (int k = 1; k < myY.length; k++) {
            yString += ", " + myY[k].getDataName();
        }
        yAxis.setLabel(yString);
        myPlot.setTitle(yString + " vs " + myX.getDataName());
        var xData = myX.getData();
        for (int i = 0; i < myY.length; i++) {
            var series = new XYChart.Series();
            series.setName(myY[i].getDataName());
            var yData = myY[i].getData();
            for (int j = 0; j < myX.getData().size() && j < myY[i].getData().size(); j++) {
                series.getData().add(new XYChart.Data(xData.get(j), yData.get(j)));
            }
            myPlot.getData().addAll(series);
        }
    }

}
