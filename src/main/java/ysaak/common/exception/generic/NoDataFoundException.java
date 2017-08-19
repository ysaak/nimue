package ysaak.common.exception.generic;

import ysaak.common.exception.BusinessException;

/**
 * Business exception indicating that no data was found during the search
 */
public class NoDataFoundException extends BusinessException {

    public NoDataFoundException(String message) {
        super(GenericErrorCode.NO_DATA_FOUND, message);
    }
}
