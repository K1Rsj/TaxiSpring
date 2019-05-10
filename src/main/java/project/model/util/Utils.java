package project.model.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;

import project.constant.EntityFields;
import project.model.dto.UserDto;

public class Utils {
    public static String getMessageInCurrentLocale(ReloadableResourceBundleMessageSource bundleMessageSource,
                                                   String message) {
        return bundleMessageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

    public static String getNameOfUniqueColumn(DataIntegrityViolationException exception) {
        if (exception.toString().contains(EntityFields.EMAIL)) {
            return EntityFields.EMAIL;
        } else {
            return EntityFields.LOGIN;
        }
    }

    public static String getValueFromNotUniqueException(DataIntegrityViolationException exception, UserDto userDto) {
        if (getNameOfUniqueColumn(exception).equals(EntityFields.EMAIL)) {
            return userDto.getEmail();
        }
        else {
            return userDto.getLogin();
        }
    }
}
