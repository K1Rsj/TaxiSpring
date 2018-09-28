package project.model.exception;

public class NoStreetWithSuchName extends Exception {

    private String streetName;

    public NoStreetWithSuchName(String message) {
        this.streetName = message;
    }

    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getMessage() {
        return "There is no street with " + streetName + " name";
    }
}
