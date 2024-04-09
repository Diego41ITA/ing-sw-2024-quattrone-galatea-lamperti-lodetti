package it.polimi.ingsw.model.exceptions;

/**
 * Exception thrown when attempting to perform an operation on an empty deck.
 */
public class illegalOperationException extends Exception {
    public illegalOperationException(String message) {
        super(message);
    }
}