package hud;

public class CategoricalDataException extends RuntimeException {
    private static final String MESSAGE = "Expected data to be numerical, but it is categorical.";

    public CategoricalDataException() {
        super(MESSAGE);
    }
}
