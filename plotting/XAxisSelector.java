package hud.plotting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A LabeledVBox that contains a dropdown selector listing all possible DataTrackers.
 *
 * @author Hunter Gregory
 */
public class XAxisSelector extends LabeledVBox {
    private static final String DESCRIPTION = "Select X data";
    private static final int NUM_OPTIONS_SHOWN = 4;

    private ComboBox<String> myDropBox;
    private DataTracker[] myTrackers;

    /**
     * Create an XAxisSelector
     * @param trackers
     */
    public XAxisSelector(DataTracker ... trackers) {
        super(DESCRIPTION);
        myTrackers = trackers;
        constructDropBox();
    }

    private void constructDropBox() {
        myDropBox = new ComboBox<>();
        myDropBox.setEditable(false);
        myDropBox.itemsProperty().set(getItemList());
        myDropBox.setVisibleRowCount(NUM_OPTIONS_SHOWN);
        myDropBox.getSelectionModel().selectFirst();
    }

    private ObservableList<String> getItemList() {
        ArrayList<String> itemList = new ArrayList<>();
        Arrays.stream(myTrackers).forEach(tracker -> itemList.add(tracker.getDataName()));
        return FXCollections.observableArrayList(itemList);
    }

    /**
     * @return the currently selected DataTracker
     */
    public DataTracker getSelectedTracker() {
        int index = myDropBox.getSelectionModel().getSelectedIndex();
        return myTrackers[index];
    }

    @Override
    protected Node getMainComponent() {
        return myDropBox;
    }
}
