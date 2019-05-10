package project.validator;

import static project.constant.EntityFields.DRIVER;
import static project.constant.EntityFields.MODEL;
import static project.constant.EntityFields.NUMBER;
import static project.constant.RegexContainer.REGEX_DRIVER_NAME;
import static project.constant.RegexContainer.REGEX_MODEL;
import static project.constant.RegexContainer.REGEX_NUMBER;
import static project.constant.ValidationMessages.WRONG_DRIVER_NAME_FORMAT;
import static project.constant.ValidationMessages.WRONG_MODEL_FORMAT;
import static project.constant.ValidationMessages.WRONG_NUMBER_FORMAT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import project.model.dto.CarDto;
import project.model.util.Utils;

@Component
public class CarValidator implements Validator {

    private final ReloadableResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public CarValidator(ReloadableResourceBundleMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return CarDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CarDto car = (CarDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, MODEL, WRONG_MODEL_FORMAT);
        if (!car.getModel().matches(Utils.getMessageInCurrentLocale(bundleMessageSource, REGEX_MODEL))) {
            errors.rejectValue(MODEL, WRONG_MODEL_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, NUMBER, WRONG_NUMBER_FORMAT);
        if (!car.getNumber().matches(Utils.getMessageInCurrentLocale(bundleMessageSource, REGEX_NUMBER))) {
            errors.rejectValue(NUMBER, WRONG_NUMBER_FORMAT);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DRIVER, WRONG_DRIVER_NAME_FORMAT);
        if (!car.getDriver().matches(Utils.getMessageInCurrentLocale(bundleMessageSource, REGEX_DRIVER_NAME))) {
            errors.rejectValue(DRIVER, WRONG_DRIVER_NAME_FORMAT);
        }
    }
}
