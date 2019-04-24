package hud;

import hud.plotting.NumericalDataTracker;
import hud.plotting.Plotter;
import hud.plotting.DataTracker;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * A Heads Up Display utility with customizable data and an optional Plotter for visualizing data.
 * Game loop is expected to store data in DataTrackers and call update on the HUDView to reflect the current values of data being stored.
 * The HUDView passes along any NumericalDataTrackers to the Plotter if plots are included in the constructor.
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

    private double myWidth;
    private double myHeight;
    private DataTracker[] myTrackers;
    private Label[] myDataLabels;
    private Label myTitle;
    private ScrollPane myScrollPane;
    private VBox myHudValuesBox;
    private VBox myPlotAndValuesBox;
    private Plotter myPlotter;
    private boolean myPlotsIncluded = false;

    /**
     * Create a HUDView
     * @param width
     * @param height
     * @param title
     * @param includePlots
     * @param trackers
     */
    public HUDView(double width, double height, String title, boolean includePlots, DataTracker ... trackers) {
        myWidth = width;
        myHeight = height;
        createTitle(title);
        myTrackers = trackers;
        myPlotter = new Plotter(myWidth, myHeight, filterTrackers(myTrackers));
        setPlotsIncluded(includePlots);
        createVBoxes();
        addLabels();
        createScrollPane();
        update();
    }

    private NumericalDataTracker[] filterTrackers(DataTracker[] trackers) {
        return Arrays.stream(trackers).filter(tracker -> tracker instanceof NumericalDataTracker).toArray(NumericalDataTracker[]::new);
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

    /**
     * Include plots if not included and vice versa.
     */
    public void togglePlotsIncluded() {
        setPlotsIncluded(!myPlotsIncluded);
    }

    private void setPlotsIncluded(boolean include) {
        if (include && !myPlotsIncluded)
            myPlotAndValuesBox.getChildren().add(myPlotter.getNode());
        else if (!include && myPlotsIncluded)
            myPlotAndValuesBox.getChildren().remove(myPlotter.getNode());
        myPlotsIncluded = include;
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

    private void createScrollPane() {
        myScrollPane = new ScrollPane(myPlotAndValuesBox);
        myScrollPane.setStyle(SCROLL_PANE_CLASS_CSS);
        setDimensions(myScrollPane);
        myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myScrollPane.setDisable(false);
    }

    private void setDimensions(Region region) {
        region.setMaxHeight(myHeight);
        region.setMinHeight(myHeight);
        region.setMinWidth(myWidth);
        region.setMaxWidth(myWidth);
    }

    private void clearText() {
        Arrays.stream(myDataLabels).forEach(label -> label.setText(""));
    }
}
