package ysaak.nimue.core.exception.user;

import ysaak.common.exception.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    LOGIN_ALREADY_USED("NIMUE-USER-B001"),
    EMAIL_ALREADY_USED("NIMUE-USER-B002"),
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
