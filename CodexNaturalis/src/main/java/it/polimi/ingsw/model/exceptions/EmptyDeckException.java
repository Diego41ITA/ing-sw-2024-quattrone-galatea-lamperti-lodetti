package it.polimi.ingsw.model.exceptions;

/**
 * Exception thrown when attempting to perform an operation on an empty deck.
 */
public class EmptyDeckException extends Exception {
    public EmptyDeckException(String message) {
        super(message);
    }
}