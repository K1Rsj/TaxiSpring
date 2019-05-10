package project.model.exception;

import static project.constant.ExceptionMessages.NO_STREET_WITH_THIS_NAME;
import static project.constant.GlobalConstants.DASH;

public class NoStreetWithSuchName extends Exception {

    private final String streetName;

    public NoStreetWithSuchName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getMessage() {
        return NO_STREET_WITH_THIS_NAME + DASH + streetName;
    }
}
