package ysaak.common.exception;

/**
 * Exception interface for all system managed exceptions
 */
public interface CloudException {
    /**
     * Returns the message string of this exception.
     * @return the message string of this {@code CloudException} instance (which may be {@code null}).
     */
    String getMessage();

    /**
     * Returns the error code of this exception.
     * @return the error code of this {@code CloudException} instance.
     */
    ErrorCode getErrorCode();

    /**
     * Returns the optional information string of this exception.
     * @return the optional information string of this {@code CloudException} instance.
     */
    default String getExtraInformation() {
        return null;
    }
}
