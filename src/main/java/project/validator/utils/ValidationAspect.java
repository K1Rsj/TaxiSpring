package project.validator.utils;

import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Aspect
@Component
public class ValidationAspect {

    private ValidationService validationService;

    @Autowired
    public ValidationAspect(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Around("execution(* project.controller..*.*(.., @project.validator.utils.DtoValidator (*),..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            DtoValidator dtoValidator = parameter.getDeclaredAnnotation(DtoValidator.class);
            ModelAttribute modelAttribute = parameter.getDeclaredAnnotation(ModelAttribute.class);
            if (dtoValidator == null) {
                continue;
            }
            BindingResult bindingResult = (BindingResult) args[1];
            validationService.validate(args[i], dtoValidator.customValidator(), bindingResult);
            RedirectAttributes redirectAttributes = (RedirectAttributes) args[2];
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult."
                    + modelAttribute.value(), bindingResult);
            redirectAttributes.addFlashAttribute(modelAttribute.value(), args[i]);
        }
        return point.proceed();
    }
}