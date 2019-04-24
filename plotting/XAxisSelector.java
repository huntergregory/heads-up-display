package hud.plotting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A LabeledVBox that contains a dropdown selector listing all possible NumericalDataTracker.
 *
 * @author Hunter Gregory
 */
public class XAxisSelector extends LabeledComboBox {
    private static final String DESCRIPTION = "Select X data";

    private NumericalDataTracker[] myTrackers;

    /**
     * Create an XAxisSelector
     * @param trackers
     */
    public XAxisSelector(NumericalDataTracker ... trackers) {
        super(DESCRIPTION);
        myTrackers = trackers;
    }

    @Override
    protected List<String> getItems() {
        return Arrays.stream(myTrackers).map(tracker -> tracker.getDataName()).collect(Collectors.toList());
    }

    /**
     * @return the currently selected NumericalDataTracker
     */
    public NumericalDataTracker getSelectedTracker() {
        return myTrackers[getSelectedIndex()];
    }
}
