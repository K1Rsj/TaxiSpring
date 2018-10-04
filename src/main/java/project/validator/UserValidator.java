package project.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.model.domain.User;
import project.model.service.UserService;

import static project.constant.ExceptionMessages.NOT_UNIQUE_LOGIN;
import static project.constant.RegexContainer.*;
import static project.constant.ValidationMessages.*;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    private ReloadableResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public UserValidator(UserService userService, ReloadableResourceBundleMessageSource bundleMessageSource) {
        this.userService = userService;
        this.bundleMessageSource = bundleMessageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", WRONG_LOGIN_FORMAT);
        if (!user.getLogin().matches(bundleMessageSource.getMessage(REGEX_LOGIN, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("login", WRONG_LOGIN_FORMAT);
        }

        if (userService.findUserByLogin(user.getLogin()).isPresent()) {
            errors.rejectValue("login", NOT_UNIQUE_LOGIN);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", WRONG_PASSWORD_FORMAT);
        if (!user.getPassword().matches(bundleMessageSource.getMessage(REGEX_PASSWORD, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("password", WRONG_PASSWORD_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationPassword", WRONG_PASSWORD_FORMAT);
        if (!user.getPassword().equals(user.getConfirmationPassword())) {
            errors.rejectValue("confirmationPassword", DIFFERENT_PASSWORDS);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", WRONG_EMAIL_FORMAT);
        if (!user.getEmail().matches(bundleMessageSource.getMessage(REGEX_EMAIL, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("email", WRONG_EMAIL_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", WRONG_FIRST_NAME_FORMAT);
        if (!user.getFirstName().matches(bundleMessageSource.getMessage(REGEX_FIRST_NAME, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("firstName", WRONG_FIRST_NAME_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secondName", WRONG_SECOND_NAME_FORMAT);
        if (!user.getSecondName().matches(bundleMessageSource.getMessage(REGEX_SECOND_NAME, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("secondName", WRONG_SECOND_NAME_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", WRONG_PHONE_NUMBER_FORMAT);
        if (!user.getPhoneNumber().matches(bundleMessageSource.getMessage(REGEX_PHONE_NUMBER, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("phoneNumber", WRONG_PHONE_NUMBER_FORMAT);
        }
    }
}
