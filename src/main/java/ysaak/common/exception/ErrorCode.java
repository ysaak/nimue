package ysaak.common.exception;

import java.io.Serializable;

public interface ErrorCode extends Serializable {
    /**
     * Get error code
     * @return
     */
    String getCode();
}
