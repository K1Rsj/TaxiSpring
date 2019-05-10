package project.model.exception;

import project.constant.GlobalConstants;

public class EntityAlreadyExists extends Exception {
    private final String fieldName;
    private final String fieldValue;

    public EntityAlreadyExists(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public String getMessage() {
        return "Entry with this " + fieldName + GlobalConstants.SPACE + fieldValue + " already exists";
    }
}
