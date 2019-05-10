package project.model.exception;

import static project.constant.ExceptionMessages.AT_THE_MOMENT_THERE_IS_NO_CAR_OF_THIS_TYPE;
import static project.constant.GlobalConstants.DASH;

public class NoFreeCarWithSuchTypeException extends Exception {

    private final String type;

    public NoFreeCarWithSuchTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return AT_THE_MOMENT_THERE_IS_NO_CAR_OF_THIS_TYPE + DASH + type;
    }
}
