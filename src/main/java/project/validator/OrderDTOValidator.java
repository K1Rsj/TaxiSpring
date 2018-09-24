package project.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import project.model.dto.OrderDTO;
import project.model.util.BundleManager;

import static project.constant.RegexContainer.REGEX_STREET;
import static project.constant.ValidationMessages.WRONG_STREET_FORMAT;

@Component
public class OrderDTOValidator implements Validator {

    private static BundleManager bundleManager = BundleManager.INSTANCE;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDTO order = (OrderDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationStreet", WRONG_STREET_FORMAT);
        if (!order.getDestinationStreet().matches(bundleManager.getString(REGEX_STREET))) {
            errors.rejectValue("destinationStreet", WRONG_STREET_FORMAT);
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureStreet", WRONG_STREET_FORMAT);
        if (!order.getDepartureStreet().matches(bundleManager.getString(REGEX_STREET))) {
            errors.rejectValue("departureStreet", WRONG_STREET_FORMAT);
        }
    }
}
