package ru.hw09.jdbc.connection;

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

            String url = "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //port
                    "db_example?" +              //db name
                    "user=tully&" +              //login
                    "password=tully&" +          //password

                    "useSSL=false";             //do not use Secure Sockets Layer


            return DriverManager.getConnection(url);
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
        ResourceBundle rb = getBundle();
        Optional<String> opt = getParamPropertiesFile(rb, str);
        if (opt.isPresent())
            return opt.get();
        return "";
    }

    private String addParam(String str, String def) {
        ResourceBundle rb = getBundle();
        Optional<String> opt = getParamPropertiesFile(rb, str);
        if (opt.isPresent())
            return opt.get();
        if (def != null) return def;
        return "";
    }

    private String addUrlParams(String... str) {
        ResourceBundle rb = getBundle();
        Optional<String> opt;

        List<String> lst = new ArrayList<>();
        for (String string : str) {
            opt = getParamPropertiesFile(rb, string);
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

    private Optional<String> getParamPropertiesFile(ResourceBundle resourceBundle, String str) {

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

    private ResourceBundle getBundle() {
        ResourceBundle rb = ResourceBundle.getBundle(propertyFile);
        return rb;
    }

}
