package com.spintech.testtask.exception;

public class ActorNotFoundException extends Exception {

    public ActorNotFoundException(String creditId) {
        super(String.format("Credit id: %s does not belong to any actor", creditId));
    }
}
