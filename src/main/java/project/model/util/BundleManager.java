package project.model.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Bundle manager
 */
@Component
public enum BundleManager {
    INSTANCE;

    /**
     * name of resource bundle
     */
    private final String RESOURCE_BUNDLE_NAME = "messages";
    /**
     * @see ResourceBundle
     */
    private ResourceBundle resourceBundle;

    BundleManager() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, Locale.getDefault());
    }

    /**
     * change resource bundle locale
     *
     * @param locale locale
     */
    public void changeLocale(Locale locale) {
        if (locale.toString().contains("uk")) {
            locale = new Locale("uk", "UA");
        }
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,
                locale);
    }

    /**
     * @param key key
     * @return string from resource by key
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

}
