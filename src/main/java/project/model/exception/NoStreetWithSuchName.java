package project.model.exception;

public class NoStreetWithSuchName extends Exception {

    private String message;

    public NoStreetWithSuchName(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "There is no street with " + message + " name";
    }
}
