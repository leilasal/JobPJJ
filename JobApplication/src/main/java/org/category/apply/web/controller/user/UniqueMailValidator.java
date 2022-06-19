package org.category.apply.web.controller.user;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import org.category.apply.HasIdAndEmail;
import org.category.apply.model.User;
import org.category.apply.repository.CrudUserRepository;

@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final CrudUserRepository crudUserRepository;

    public UniqueMailValidator(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndEmail user = ((HasIdAndEmail) target);
        if (StringUtils.hasText(user.getEmail())) {
            User dbUser = crudUserRepository.getByEmail(user.getEmail().toLowerCase());
            if (dbUser != null && !dbUser.getId().equals(user.getId())) {
                errors.rejectValue("email", "User with this email already exists");
            }
        }
    }
}
