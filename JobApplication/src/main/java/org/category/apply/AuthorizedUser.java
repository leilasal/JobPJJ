package org.category.apply;

import org.category.apply.model.User;
import org.category.apply.to.UserTo;
import org.category.apply.util.mapper.UserMapper;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 42L;

    private UserTo userTo;

    public AuthorizedUser(UserMapper mapper, User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = mapper.toTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
