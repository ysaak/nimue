package ysaak.nimue.core.exception.user;

import ysaak.common.exception.BusinessException;

public class EmailAlreadyUsedException extends BusinessException {
    public EmailAlreadyUsedException(String email) {
        super(UserErrorCode.EMAIL_ALREADY_USED, "Email " + email + " already used");
    }
}
