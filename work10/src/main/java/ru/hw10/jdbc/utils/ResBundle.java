package ru.hw10.jdbc.utils;

import java.util.*;

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

    public static String readAllFile(ResourceBundle resourceBundle) {
        Enumeration bundleKeys = resourceBundle.getKeys();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        while (bundleKeys.hasMoreElements()) {
            stringBuilder.append(" , ");
            String key = (String) bundleKeys.nextElement();
            stringBuilder.append(resourceBundle.getString(key));

        }
        stringBuilder.append(");");
        return stringBuilder.toString().replaceFirst(" ,", "");

    }

    public static Map<String, Set<String>> readAllForeignKeys(String table, ResourceBundle resourceBundle) {
        Map<String, Set<String>> map = new HashMap<>();
        Enumeration bundleKeys = resourceBundle.getKeys();
        if (table != null || !table.isEmpty()) {
            while (bundleKeys.hasMoreElements()) {
                String key = (String) bundleKeys.nextElement();
                String fKey = resourceBundle.getString(key);
                if (fKey.toLowerCase().contains("foreign key")) {
                    Set<String> set = map.get(table);
                    if (set == null) set = new TreeSet<>();
                    set.add(fKey);
                    map.put(table,set);
                }
            }
        }
        return map;
    }

}
