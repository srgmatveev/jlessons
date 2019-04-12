package ru.hw09.jdbc.utils;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ResBundle {
    public static Optional<String> getParamPropertiesFile(ResourceBundle resourceBundle, String str) {

        if (resourceBundle == null) return Optional.empty();
        if (str == null || str.isEmpty()) return Optional.empty();
        String res = null;
        try {
            res = resourceBundle.getString(str);
        } catch (MissingResourceException e) {
            return Optional.empty();
        }
        return res.isEmpty() ? Optional.empty() : Optional.of(res);

    }

    public static ResourceBundle getBundle(String propertyFile) {
        ResourceBundle rb = ResourceBundle.getBundle(propertyFile);
        return rb;
    }

}
