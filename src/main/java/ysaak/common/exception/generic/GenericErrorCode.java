package ysaak.common.exception.generic;

import ysaak.common.exception.ErrorCode;

public enum GenericErrorCode implements ErrorCode {
    NO_DATA_FOUND("GEN-DATA-001"),
    INVALID_DATA("GEN-DATA-002")
    ;

    private final String code;

    GenericErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
