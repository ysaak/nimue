package ysaak.nimue.core.exception.user;

import ysaak.common.exception.BusinessException;

public class NoMatchingPasswordException extends BusinessException {
    public NoMatchingPasswordException() {
        super(UserErrorCode.NO_MATCHING_PASSWORD, "Passwords don't match");
    }
}
