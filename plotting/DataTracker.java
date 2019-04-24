package hud.plotting;

import hud.CategoricalDataException;

import java.util.ArrayList;

/**
 * This class is used to store data for a desired numerical variable
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
     * @return true if data is numerical and safe to get in Double form
     */
    public boolean isNumerical() {
        try {
            getDoubleData();
            return true;
        }
        catch (CategoricalDataException e) {
            return false;
        }
    }

    /**
     * @return list of data as numerical values
     * @throws CategoricalDataException
     */
    public ArrayList<Double> getDoubleData() throws CategoricalDataException {
        ArrayList<Double> doubles = new ArrayList<>();
        for (int k=0; k<myData.size(); k++)
            doubles.add(getDouble(k));
        return doubles;
    }

    /**
     * @return most recently added data value
     */
    public T getLatestValue() {
        return myData.get(myData.size() - 1);
    }

    /**
     * @return most recently added data value as a numerical value
     * @throws CategoricalDataException if data can't be converted to a Double
     */
    public Double getLatestDouble() throws CategoricalDataException {
        return getDouble(myData.size() - 1);
    }

    private Double getDouble(int index) throws CategoricalDataException {
        try {
            return Double.parseDouble(myData.get(index).toString());
        }
        catch (NumberFormatException e) {
            throw new CategoricalDataException();
        }
    }
}