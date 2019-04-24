package hud.plotting;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * A tool for displaying a plot with axes specified by a mini GUI.
 * Combines the functionality of XAxisSelector, YAxisSelector, and PlotBuilder into one displayable entity.
 * Filters out non-numerical DataTrackers passed into the constructor so that the user doesn't have the chance to try to plot categorical data.
 *
 * @author Hunter Gregory
 */
public class Plotter {
    private static final double VERTICAL_SPACING = 20;
    private static final double HORIZONTAL_SPACING = 25;

    private XAxisSelector myXAxisSelector;
    private YAxisSelector myYAxisSelector;
    private VBox myVBox;
    private double myWidth;
    private double myHeight;

    /**
     * Create a Plotter with a given array of possible data to plot.
     * @param width
     * @param height
     * @param trackers
     */
    public Plotter(double width, double height, DataTracker ... trackers) {
        myWidth = width;
        myHeight = height;
        DataTracker[] filtered = filterTrackers(trackers);
        myXAxisSelector = new XAxisSelector(filtered);
        myYAxisSelector = new YAxisSelector(filtered);
        setupDisplay();
        updateGraph();
    }

    /**
     * Update graph with new data tracker values
     */
    public void updateGraph() {
        myVBox.getChildren().set(0, getCurrentGraph());
    }

    /**
     * @return display including plot and mini GUI
     */
    public Node getNode() {
        return myVBox;
    }

    private DataTracker[] filterTrackers(DataTracker[] trackers) {
        ArrayList<DataTracker> filtered = new ArrayList<>();
        for (DataTracker tracker : trackers) {
            if (tracker.isNumerical())
                filtered.add(tracker);
        }
        return filtered.toArray(new DataTracker[0]);
    }

    private void setupDisplay() {
        var miniGUI = new HBox(myYAxisSelector.getVBox(), myXAxisSelector.getVBox());
        miniGUI.setSpacing(HORIZONTAL_SPACING);
        miniGUI.setAlignment(Pos.CENTER);
        myVBox = new VBox(getCurrentGraph(), miniGUI);
        myVBox.setSpacing(VERTICAL_SPACING);
    }

    private ScatterChart<Number, Number> getCurrentGraph() {
        var plotBuilder = new PlotBuilder(myXAxisSelector.getSelectedTracker(), myYAxisSelector.getSelectedTrackers(), myWidth, myHeight);
        return plotBuilder.createPlot();
    }
}
