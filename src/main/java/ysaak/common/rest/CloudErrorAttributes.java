package ysaak.common.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import ysaak.common.exception.CloudException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CloudErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();

        Throwable throwable = getError(requestAttributes);
        if (throwable != null) {
            /*Throwable cause = throwable.getCause();*/
            if (throwable instanceof CloudException) {
                CloudException e = (CloudException) throwable;
                fillAttributes(errorAttributes, e.getErrorCode().getCode(), e.getMessage(), e.getExtraInformation());
            }
            else {
                fillAttributes(errorAttributes, "SYSTEM-UNKNOWN-T002", throwable.getMessage(), throwable.getClass().getName());
            }
        }
        else {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Authentication error
                fillAttributes(errorAttributes, "SYSTEM-AUTH-T001", "Access Denied", null);
            }
            else {
                fillAttributes(errorAttributes, "SYSTEM-UNKNOWN-T002", "Unexpected error", null);
            }
        }

        return errorAttributes;
    }

    private void fillAttributes(final Map<String, Object> errorAttributes, final String error, final String message, final String extraInfo) {
        errorAttributes.put("error", error);
        errorAttributes.put("message", message);
        if (StringUtils.isNotBlank(extraInfo)) {
            errorAttributes.put("information", extraInfo);
        }
        errorAttributes.put("timestamp", new Date());
    }
}
