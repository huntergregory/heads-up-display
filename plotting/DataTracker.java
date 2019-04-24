package hud.plotting;

import java.util.ArrayList;

/**
 * This class is used to store data for any type of variable
 * @author Carter Gay
 */
public class DataTracker<T> {

    private ArrayList<T> myData;
    private String myDataName;

    /**
     * DataTracker constructor where the name identifies the variable stored
     * @param name
     */
    public DataTracker(String name) {
        myDataName = name;
        myData = new ArrayList<>();
    }

    /**
     * Add a data point to the List
     * @param x
     */
    public void storeData(T x) {
        myData.add(x);
    }

    /**
     * Get the name of the data being stored
     * @return
     */
    public String getDataName() {
        return myDataName;
    }

    /**
     * Get the data values as type T
     * @return
     */
    public ArrayList<T> getData() {
        return myData;
    }

    /**
     * @return most recently added data value
     */
    public T getLatestValue() {
        return myData.get(myData.size() - 1);
    }
}