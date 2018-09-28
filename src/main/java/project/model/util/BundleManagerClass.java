package project.model.util;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class BundleManagerClass {

    /**
     * name of resource bundle
     */
    private String resourceBundleName = "messages";
    /**
     * @see ResourceBundle
     */
    private ResourceBundle resourceBundle;

    BundleManagerClass() {
        resourceBundle = ResourceBundle.getBundle(resourceBundleName, LocaleContextHolder.getLocale());
    }

    public String getResourceBundleName() {
        return resourceBundleName;
    }

    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }

    /**
     * @param key key
     * @return string from resource by key
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    @Lookup
    public BundleManagerClass getObject() {
        return null;
    }
}
