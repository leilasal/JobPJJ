package org.category.apply.service;

import org.category.apply.AuthorizedUser;
import org.category.apply.model.User;
import org.category.apply.repository.CrudUserRepository;
import org.category.apply.to.UserTo;
import org.category.apply.util.mapper.UserMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static org.category.apply.util.UserUtil.prepareToSave;
import static org.category.apply.util.validation.ValidationUtil.checkNotFound;
import static org.category.apply.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {
    private static final Sort SORT_BY_DATE = Sort.by(Sort.Direction.DESC, "registered");

    private final CrudUserRepository crudUserRepository;
    private final UserMapper mapper;

    public UserService(CrudUserRepository crudUserRepository, UserMapper mapper) {
        this.crudUserRepository = crudUserRepository;
        this.mapper = mapper;
    }

    public User create(User user) {
        Assert.notNull(user, "User must be not null");
        return prepareAndSave(user);
    }

    public User get(int id) {
        return checkNotFoundWithId(crudUserRepository.findById(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "Email must be not null");
        return checkNotFound(crudUserRepository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_BY_DATE);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        save(user);
    }

    public void update(User user) {
        Assert.notNull(user, "User must be not null");
        prepareAndSave(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        Assert.notNull(userTo, "UserTo must be not null");
        User user = get(userTo.getId());
        mapper.updateEntity(user, userTo);
        prepareAndSave(user);
    }

    public void delete(int id) {
        crudUserRepository.deleteExisted(id);
    }

    private User prepareAndSave(User user) {
        return save(prepareToSave(user));
    }

    @Transactional
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = crudUserRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(mapper, user);
    }
}
