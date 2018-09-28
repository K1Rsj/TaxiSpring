package project.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.model.domain.User;
import project.model.service.UserService;
import project.model.util.BundleManagerClass;

import static project.constant.ExceptionMessages.NOT_UNIQUE_LOGIN;
import static project.constant.RegexContainer.*;
import static project.constant.ValidationMessages.*;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    private BundleManagerClass bundleManager;

    @Autowired
    public UserValidator(UserService userService, BundleManagerClass bundleManager) {
        this.userService = userService;
        this.bundleManager = bundleManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", WRONG_LOGIN_FORMAT);
        if (!user.getLogin().matches(bundleManager.getObject().getString(REGEX_LOGIN))) {
            errors.rejectValue("login", WRONG_LOGIN_FORMAT);
        }

        if (userService.findUserByLogin(user.getLogin()).isPresent()) {
            errors.rejectValue("login", NOT_UNIQUE_LOGIN);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", WRONG_PASSWORD_FORMAT);
        if (!user.getPassword().matches(bundleManager.getObject().getString(REGEX_PASSWORD))) {
            errors.rejectValue("password", WRONG_PASSWORD_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword", WRONG_PASSWORD_FORMAT);
        if (!user.getPassword().equals(user.getConfirmationPassword())) {
            errors.rejectValue("confirmationPassword", DIFFERENT_PASSWORDS);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", WRONG_EMAIL_FORMAT);
        if (!user.getEmail().matches(bundleManager.getObject().getString(REGEX_EMAIL))) {
            errors.rejectValue("email", WRONG_EMAIL_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", WRONG_FIRST_NAME_FORMAT);
        if (!user.getFirstName().matches(bundleManager.getObject().getString(REGEX_FIRST_NAME))) {
            errors.rejectValue("firstName", WRONG_FIRST_NAME_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondName", WRONG_SECOND_NAME_FORMAT);
        if (!user.getSecondName().matches(bundleManager.getObject().getString(REGEX_SECOND_NAME))) {
            errors.rejectValue("secondName", WRONG_SECOND_NAME_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", WRONG_PHONE_NUMBER_FORMAT);
        if (!user.getPhoneNumber().matches(bundleManager.getObject().getString(REGEX_PHONE_NUMBER))) {
            errors.rejectValue("phoneNumber", WRONG_PHONE_NUMBER_FORMAT);
        }
    }
}
