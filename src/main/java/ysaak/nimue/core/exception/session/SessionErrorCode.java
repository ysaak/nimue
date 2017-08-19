package ysaak.nimue.core.exception.session;

import ysaak.common.exception.ErrorCode;

public enum SessionErrorCode implements ErrorCode {
    INVALID_CREDENTIALS("NIMUE-SESS-B001"),

    ;

    private final String code;

    SessionErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
