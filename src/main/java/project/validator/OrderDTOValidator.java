package project.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.model.dto.OrderDTO;

import static project.constant.RegexContainer.REGEX_STREET;
import static project.constant.ValidationMessages.WRONG_STREET_FORMAT;

@Component
public class OrderDTOValidator implements Validator {

    private final ReloadableResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public OrderDTOValidator(ReloadableResourceBundleMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDTO order = (OrderDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationStreet", WRONG_STREET_FORMAT);
        if (!order.getDestinationStreet().matches(bundleMessageSource.getMessage(REGEX_STREET, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("destinationStreet", WRONG_STREET_FORMAT);
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureStreet", WRONG_STREET_FORMAT);
        if (!order.getDepartureStreet().matches(bundleMessageSource.getMessage(REGEX_STREET, null, LocaleContextHolder.getLocale()))) {
            errors.rejectValue("departureStreet", WRONG_STREET_FORMAT);
        }
    }
}
