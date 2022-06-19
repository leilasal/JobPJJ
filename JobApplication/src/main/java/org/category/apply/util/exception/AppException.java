package org.category.apply.util.exception;

import lombok.Getter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class AppException extends ResponseStatusException {
    private final transient ErrorAttributeOptions options;

    public AppException(HttpStatus status, String message, ErrorAttributeOptions options) {
        super(status, message);
        this.options = options;
    }

    @Override
    public String getMessage() {
        return NestedExceptionUtils.buildMessage(getReason(), this.getCause());
    }
}
