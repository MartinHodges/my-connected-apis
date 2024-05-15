package com.requillion_solutions.my_connected_apis.exceptions;

public class ResourceNotFoundException extends EntityException {

    private static final long serialVersionUID = 4240546934827596019L;
    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
