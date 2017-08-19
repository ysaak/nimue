package ysaak.nimue.core.exception.user;

import ysaak.common.exception.BusinessException;

public class LoginAlreadyUsedException extends BusinessException {
    public LoginAlreadyUsedException(String login) {
        super(UserErrorCode.LOGIN_ALREADY_USED, "Login " + login + " already used");
    }
}
