package project.model.exception;

public class NoFreeCarWithSuchTypeException extends Exception {
    @Override
    public String getMessage() {
        return "No free car with this car type";
    }
}
