package project.validator;

import static project.constant.EntityFields.CONFIRMATION_PASSWORD;
import static project.constant.EntityFields.EMAIL;
import static project.constant.EntityFields.FIRST_NAME;
import static project.constant.EntityFields.LOGIN;
import static project.constant.EntityFields.PASSWORD;
import static project.constant.EntityFields.PHONE_NUMBER;
import static project.constant.EntityFields.SECOND_NAME;
import static project.constant.ExceptionMessages.NOT_UNIQUE_LOGIN;
import static project.constant.RegexContainer.REGEX_EMAIL;
import static project.constant.RegexContainer.REGEX_FIRST_NAME;
import static project.constant.RegexContainer.REGEX_LOGIN;
import static project.constant.RegexContainer.REGEX_PASSWORD;
import static project.constant.RegexContainer.REGEX_PHONE_NUMBER;
import static project.constant.RegexContainer.REGEX_SECOND_NAME;
import static project.constant.ValidationMessages.DIFFERENT_PASSWORDS;
import static project.constant.ValidationMessages.WRONG_EMAIL_FORMAT;
import static project.constant.ValidationMessages.WRONG_FIRST_NAME_FORMAT;
import static project.constant.ValidationMessages.WRONG_LOGIN_FORMAT;
import static project.constant.ValidationMessages.WRONG_PASSWORD_FORMAT;
import static project.constant.ValidationMessages.WRONG_PHONE_NUMBER_FORMAT;
import static project.constant.ValidationMessages.WRONG_SECOND_NAME_FORMAT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import project.model.dto.UserDto;
import project.model.service.UserService;

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
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto user = (UserDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, LOGIN, WRONG_LOGIN_FORMAT);
        if (!user.getLogin().matches(bundleMessageSource.getMessage(REGEX_LOGIN, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(LOGIN, WRONG_LOGIN_FORMAT);
        }

        if (userService.findUserByLogin(user.getLogin()).isPresent()) {
            errors.rejectValue(LOGIN, NOT_UNIQUE_LOGIN);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PASSWORD, WRONG_PASSWORD_FORMAT);
        if (!user.getPassword().matches(bundleMessageSource.getMessage(REGEX_PASSWORD, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(PASSWORD, WRONG_PASSWORD_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CONFIRMATION_PASSWORD, WRONG_PASSWORD_FORMAT);
        if (!user.getPassword().equals(user.getConfirmationPassword())) {
            errors.rejectValue(CONFIRMATION_PASSWORD, DIFFERENT_PASSWORDS);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL, WRONG_EMAIL_FORMAT);
        if (!user.getEmail().matches(bundleMessageSource.getMessage(REGEX_EMAIL, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(EMAIL, WRONG_EMAIL_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIRST_NAME, WRONG_FIRST_NAME_FORMAT);
        if (!user.getFirstName().matches(bundleMessageSource.getMessage(REGEX_FIRST_NAME, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(FIRST_NAME, WRONG_FIRST_NAME_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, SECOND_NAME, WRONG_SECOND_NAME_FORMAT);
        if (!user.getSecondName().matches(bundleMessageSource.getMessage(REGEX_SECOND_NAME, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(SECOND_NAME, WRONG_SECOND_NAME_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PHONE_NUMBER, WRONG_PHONE_NUMBER_FORMAT);
        if (!user.getPhoneNumber().matches(bundleMessageSource.getMessage(REGEX_PHONE_NUMBER, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(PHONE_NUMBER, WRONG_PHONE_NUMBER_FORMAT);
        }
    }
}
