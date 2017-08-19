package ysaak.nimue.core.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ysaak.common.exception.BusinessException;
import ysaak.common.exception.generic.DataValidationException;
import ysaak.common.service.AbstractService;
import ysaak.nimue.core.exception.user.UserErrorCode;
import ysaak.nimue.core.model.User;
import ysaak.nimue.core.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserService extends AbstractService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    private void beforeSave(User user, boolean passwordRequired) throws BusinessException {
        validateData(user);

        // Check login already exists
        User existingUser = findByLogin(user.getLogin());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new BusinessException(UserErrorCode.LOGIN_ALREADY_EXISTS, "login already existing");
        }

        // Check passwords
        if (passwordRequired && user.getPassword() == null && user.getPasswordConfirmation() == null) {
            throw new DataValidationException(Arrays.asList("password", "passwordConfirmation"));
        }

        if (!StringUtils.equals(user.getPassword(), user.getPasswordConfirmation())) {
            throw new BusinessException(UserErrorCode.NO_MATCHING_PASSWORD, "Passwords don't match");
        }
    }

    public void create(User user) throws BusinessException {
        beforeSave(user, true);

        user.setPasswordDigest(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(User user) throws BusinessException {
        beforeSave(user, false);

        if (Objects.nonNull(user.getPassword())) {
            // change password if sets
            user.setPasswordDigest(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    /**
     * Find a user by its login
     * @param login Login of the user
     * @return User data
     */
    public User findByLogin(String login) {
        Validate.notNull(login, "login must not be null");
        return userRepository.findByLogin(login);
    }

    public boolean checkPasswordMatches(User user, String password) {
        // Validate current password
        String encodedPassword = passwordEncoder.encode(password);
        return StringUtils.equals(encodedPassword, user.getPasswordDigest());
    }

    public List<User> listAll() {
        return toList(userRepository.findAll());
    }
}
