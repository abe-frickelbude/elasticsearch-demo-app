package de.frickelbude.es.sandbox.service;

/**
 * Simple catch-all exception for various cases where I want to convert a checked exception into an unchecked one, so I
 * can arrange for some centralized exception handling...
 * 
 * @author Ibragim Kuliev
 *
 */
public class EsSandboxException extends RuntimeException {

    public EsSandboxException() {
        super();
    }

    public EsSandboxException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EsSandboxException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EsSandboxException(final String message) {
        super(message);
    }

    public EsSandboxException(final Throwable cause) {
        super(cause);
    }
}
