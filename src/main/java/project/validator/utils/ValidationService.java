package project.validator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ValidationService {
    private ApplicationContext applicationContext;

    @Autowired
    public ValidationService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void validate(Object object, Class<? extends Validator> validatorClass, BindingResult bindingResult) {
        Validator validator = applicationContext.getBean(validatorClass);
        ValidationUtils.invokeValidator(validator, object, bindingResult);
    }
}
