package io.github.qwzhang01.operating.exception;

/**
 * Unsupported Operation Exception.
 * <p>
 * This runtime exception is thrown when an operation is attempted
 * that is not supported by the current strategy implementation.
 *
 * @author avinzhang
 */
public class NonsupportedOpException extends RuntimeException {
    public NonsupportedOpException() {
        super("Unsupported operation");
    }

    public NonsupportedOpException(String message) {
        super(message);
    }
}
