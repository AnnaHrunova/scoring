package org.inbank.scoring.exception;

public class MissingDataException extends RuntimeException {

    public MissingDataException(String unit, String id) {
        super(String.format("Unable to find: %s with identifier: %s", unit, id));
    }
}
