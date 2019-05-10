package project.validator;

import static project.constant.EntityFields.DEPARTURE_STREET;
import static project.constant.EntityFields.DESTINATION_STREET;
import static project.constant.RegexContainer.REGEX_STREET;
import static project.constant.ValidationMessages.WRONG_STREET_FORMAT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import project.model.dto.OrderDto;

@Component
public class OrderValidator implements Validator {

    private final ReloadableResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public OrderValidator(ReloadableResourceBundleMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDto order = (OrderDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DESTINATION_STREET, WRONG_STREET_FORMAT);
        if (!order.getDestinationStreet().matches(bundleMessageSource.getMessage(REGEX_STREET, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(DESTINATION_STREET, WRONG_STREET_FORMAT);
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DEPARTURE_STREET, WRONG_STREET_FORMAT);
        if (!order.getDepartureStreet().matches(bundleMessageSource.getMessage(REGEX_STREET, null,
                LocaleContextHolder.getLocale()))) {
            errors.rejectValue(DEPARTURE_STREET, WRONG_STREET_FORMAT);
        }
    }
}
