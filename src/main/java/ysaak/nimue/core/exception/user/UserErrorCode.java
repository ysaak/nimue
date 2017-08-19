package ysaak.nimue.core.exception.user;

import ysaak.common.exception.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    UNKNOWN_USER("NIMUE-USER-B001"),

    LOGIN_ALREADY_EXISTS("NIMUE-USER-B002"),
    NO_MATCHING_PASSWORD("NIMUE-USER-B003")
    ;

    private final String code;

    UserErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
