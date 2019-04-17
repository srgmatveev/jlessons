package ru.hw10.jdbc.connection;

import ru.hw10.jdbc.utils.ResBundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class MySQLConnectionHelper implements ConnectionHelper {
    private String propertyFile;

    public MySQLConnectionHelper() {
        super();
        propertyFile = "db";
    }

    public MySQLConnectionHelper(String propertyFile) {
        super();
        this.propertyFile = propertyFile;
    }

    @Override
    public Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(buildUrl());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUrl() {
        StringBuilder url = new StringBuilder();
        url.append(addParam("url"));
        url.append(addParam("host"));
        url.append(":");
        url.append(addParam("port", "3306"));
        url.append("/");
        url.append(addParam("database"));
        url.append(addUrlParams(new String[]{"user", "password", "useSSL"}));
        return url.toString();
    }

    private String addParam(String str) {
        ResourceBundle rb = ResBundle.getBundle(propertyFile);
        Optional<String> opt = ResBundle.getParamPropertiesFile(rb, str);
        if (opt.isPresent())
            return opt.get();
        return "";
    }

    private String addParam(String str, String def) {
        ResourceBundle rb = ResBundle.getBundle(propertyFile);
        Optional<String> opt = ResBundle.getParamPropertiesFile(rb, str);
        if (opt.isPresent())
            return opt.get();
        if (def != null) return def;
        return "";
    }

    private String addUrlParams(String... str) {
        ResourceBundle rb = ResBundle.getBundle(propertyFile);
        Optional<String> opt;

        List<String> lst = new ArrayList<>();
        for (String string : str) {
            opt = ResBundle.getParamPropertiesFile(rb, string);
            if (opt.isPresent())
                lst.add(String.format("%s=%s", string, opt.get()));
        }

        int n = lst.size();
        if (n > 0) {
            StringBuilder sb = new StringBuilder("?");

            for (int i = 0; i < n; ++i) {
                sb.append(lst.get(i));
                if (i < (n - 1)) sb.append("&");
            }
            return sb.toString();
        }

        return "";
    }





}
