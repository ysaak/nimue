package ysaak.common.exception.generic;

import org.apache.commons.lang3.Validate;
import ysaak.common.exception.BusinessException;

import java.util.List;

public class DataValidationException extends BusinessException {
    private static final String FIELD_VALIDATION_SEPARATOR = ";";

    private List<String> invalidFields;

    public DataValidationException(List<String> invalidFields) {
        super(GenericErrorCode.INVALID_DATA, "Object is invalid");
        this.invalidFields = Validate.notNull(invalidFields, "invalidFields is null");
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }

    @Override
    public String getExtraInformation() {
        return String.join(FIELD_VALIDATION_SEPARATOR, invalidFields);
    }
}
