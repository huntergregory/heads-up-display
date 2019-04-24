package hud.plotting;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to construct plots to dynamically display game variables
 * @author Carter Gay
 */
public class PlotBuilder {

    private XYChart.Series mySeries;
    private ArrayList<Double> myX;
    private ArrayList<Double>[] myY;
    private String xName;
    private String[] yName;
    private double myWidth;
    private double myHeight;

    /**
     * PlotBuilder constructor that takes in an x feature and any number of y features
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public PlotBuilder(DataTracker x, List<DataTracker> y, double width, double height) {
        myX = x.getData();
        xName = x.getDataName();
        myY = new ArrayList[y.size()];
        yName = new String[y.size()];
        myWidth = width;
        myHeight = height;
        for (int i = 0; i < y.size(); i++) {
            myY[i]= y.get(i).getData();
            yName[i] = y.get(i).getDataName();
        }
    }

    /**
     * Creates the scatter plot of the passed in x and y features with appropriate titles/labels
     * @return
     */
    public ScatterChart<Number,Number> createPlot() {
        var xAxis = new NumberAxis();
        xAxis.setLabel(xName);
        var yAxis = new NumberAxis();
        var sc = new ScatterChart<>(xAxis,yAxis);
        sc.setAnimated(false);
        sc.setMaxWidth(myWidth);
        sc.setMaxHeight(myHeight);
        if (myY.length != 0)
            plotData(yAxis, sc);
        else
            sc.setTitle("Please select y");
        return sc;
    }

    private void plotData(NumberAxis yAxis, ScatterChart<Number, Number> sc) {
        String yString = yName[0];
        for (int k=1; k<yName.length; k++)
            yString += ", " + yName[k];
        yAxis.setLabel(yString);
        sc.setTitle(yString + " vs " + xName);
        for (int i = 0; i < myY.length; i++) {
            mySeries = new XYChart.Series();
            mySeries.setName(yName[i]);
            for (int j = 0; j < myX.size(); j++) {
                mySeries.getData().add(new XYChart.Data(myX.get(j), myY[i].get(j)));
            }
            sc.getData().addAll(mySeries);
        }
    }

}
