package hud.plotting;

/**
 * A DataTracker that's guaranteed to have numerical data.
 * @param <T>
 */
public class NumericalDataTracker<T extends Number> extends DataTracker {
    public NumericalDataTracker(String name) {
        super(name);
    }
}
