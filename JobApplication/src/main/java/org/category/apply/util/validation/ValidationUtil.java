package org.category.apply.util.validation;

import org.category.apply.HasId;
import org.category.apply.util.exception.IllegalRequestDataException;
import org.category.apply.util.exception.NotFoundException;

import java.util.Optional;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new NotFoundException("Operation on entity with id=" + id + " not succeeded");
        }
    }

    public static <T> T checkNotFoundWithId(Optional<T> object, int id) {
        checkNotFound(object.isPresent(), "id=" + id);
        return object.get();
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean isFound, int id) {
        checkNotFound(isFound, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String message) {
        checkNotFound(object != null, message);
        return object;
    }

    public static void checkNotFound(boolean isFound, String message) {
        if (!isFound) {
            throw new NotFoundException("Not found entity with " + message);
        }
    }
}