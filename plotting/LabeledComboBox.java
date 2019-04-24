package hud.plotting;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.util.List;

public abstract class LabeledComboBox<T> extends LabeledVBox {
    private static final int NUM_OPTIONS_SHOWN = 4;

    private ComboBox<String> myDropBox;

    public LabeledComboBox(String labelText) {
        super(labelText);
    }

    /**
     * @return String items to be displayed in ComboBox
     */
    abstract protected List<String> getItems();

    protected int getSelectedIndex() {
        return myDropBox.getSelectionModel().getSelectedIndex();
    }

    private void constructDropBox() {
        myDropBox = new ComboBox<>();
        myDropBox.setEditable(false);
        myDropBox.itemsProperty().set(FXCollections.observableArrayList(getItems()));
        myDropBox.setVisibleRowCount(NUM_OPTIONS_SHOWN);
        myDropBox.getSelectionModel().selectFirst();
    }

    @Override
    protected Node getMainComponent() {
        if (myDropBox == null)
            constructDropBox();
        return myDropBox;
    }
}
