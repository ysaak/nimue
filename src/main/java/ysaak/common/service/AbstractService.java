package ysaak.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ysaak.common.exception.BusinessException;
import ysaak.common.exception.generic.DataValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AbstractService {
    private static final String DEFAULT_USERNAME = "admin";

    /**
     * Validates data
     *
     * @param data Object to validates
     * @throws BusinessException Thrown if the object is not valid
     */
    protected void validateData(Object data) throws BusinessException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(data);

        List<String> invalidFields = new ArrayList<>();

        for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {
            if (constraintViolation.getPropertyPath().iterator().hasNext()) {
                invalidFields.add(constraintViolation.getPropertyPath().iterator().next().getName());
            }
        }
        if (invalidFields.size() > 0) {
            throw new DataValidationException(invalidFields);
        }
    }

    protected String getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getName();
        }

        return DEFAULT_USERNAME;
    }

    /**
     * Transform an iterable to a arraylist
     *
     * @param iterable
     *          Iterable to transform
     * @param <E>
     *          Type of the collection
     * @return List of data
     */
    protected <E> List<E> toList(Iterable<E> iterable) {
        List<E> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
