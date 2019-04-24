package hud;

import hud.plotting.Plotter;
import hud.plotting.DataTracker;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Heads Up Display utility with customizable data and an optional Plotter for visualizing data.
 * Declare the name of each data type for display purposes,
 * and provide an array of corresponding data Object to display next to each name.
 * Update the HUD by providing another array of data Objects.
 * In a game loop, we recommend implementing a getHUDValues method and calling HUD.update(getHUDValues)
 * at the end of each iteration (or every so often).
 *
 * @author Hunter Gregory
 * @author Carter Gay
 */
public class HUDView {
    private static final String TITLE_ID_CSS = "hud-title";
    private static final String DATA_LABEL_CLASS_CSS = "data-label";
    private static final String SCROLL_PANE_CLASS_CSS = "scroll-pane";
    private static final double INTER_VALUES_SPACING = 2;
    private static final double PLOT_VALUES_SPACING = 20;

    private DataTracker[] myTrackers;
    private Label[] myDataLabels;
    private Label myTitle;
    private ScrollPane myScrollPane;
    private VBox myHudValuesBox;
    private VBox myPlotAndValuesBox;
    private Plotter myPlotter = null;

    /**
     * Create a HUDView
     * @param width
     * @param height
     * @param title
     * @param includePlots
     * @param trackers
     */
    public HUDView(double width, double height, String title, boolean includePlots, DataTracker ... trackers) {
        createTitle(title);
        myTrackers = trackers;
        if (includePlots) {
            myPlotter = new Plotter(width, height, filterTrackers(myTrackers));
            myPlotAndValuesBox.getChildren().add(myPlotter.getNode());
        }
        createVBoxes();
        addLabels();
        createScrollPane(width, height);
        update();
    }

    private DataTracker[] filterTrackers(DataTracker[] trackers) {
        return Arrays.stream(trackers).filter(DataTracker::isNumerical).toArray(DataTracker[]::new);
    }

    private void createVBoxes() {
        myHudValuesBox = new VBox();
        myHudValuesBox.setSpacing(INTER_VALUES_SPACING);
        myPlotAndValuesBox = new VBox(myHudValuesBox);
        myPlotAndValuesBox.setSpacing(PLOT_VALUES_SPACING);
    }

    /**
     * @return the HUD display
     */
    public Node getNode() {
        return myScrollPane;
    }

    /**
     * Updates the values displayed in the HUD and updates any plots if applicable.
     */
    public void update() {
        clearText();
        for (int k = 0; k< myTrackers.length; k++) {
            var tracker = myTrackers[k];
            myDataLabels[k].setText(tracker.getDataName() + ": " + tracker.getLatestValue().toString());
        }

        if (myPlotter != null)
            myPlotter.updateGraph();
    }

    /**
     * Update the title of the HUD
     * @param title
     */
    public void setNewTitle(String title) {
        myTitle.setText(title);
    }

    private void createTitle(String title) {
        myTitle = new Label(title);
        myTitle.setStyle(DATA_LABEL_CLASS_CSS);
        myTitle.setId(TITLE_ID_CSS);
    }

    private void addLabels() {
        myHudValuesBox.getChildren().add(myTitle);

        myDataLabels = new Label[myTrackers.length];
        for (int k = 0; k< myDataLabels.length; k++) {
            var label = new Label();
            label.setStyle(DATA_LABEL_CLASS_CSS);
            myHudValuesBox.getChildren().add(label);
            myDataLabels[k] = label;
        }
    }

    private void createScrollPane(double width, double height) {
        myScrollPane = new ScrollPane(myPlotAndValuesBox);
        myScrollPane.setStyle(SCROLL_PANE_CLASS_CSS);
        setDimensions(myScrollPane, width, height);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
    }

    private void setDimensions(Region region, double width, double height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
        region.setMinWidth(width);
        region.setMaxWidth(width);
    }

    private void clearText() {
        Arrays.stream(myDataLabels).forEach(label -> label.setText(""));
    }
}
