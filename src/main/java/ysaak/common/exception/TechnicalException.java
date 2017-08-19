package ysaak.common.exception;

import org.apache.commons.lang3.Validate;

public class TechnicalException extends Exception implements CloudException {
    private final ErrorCode errorCode;

    public TechnicalException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = Validate.notNull(errorCode);
    }

    public TechnicalException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = Validate.notNull(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
