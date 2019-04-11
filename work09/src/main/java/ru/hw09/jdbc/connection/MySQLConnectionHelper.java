package ru.hw09.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySQLConnectionHelper implements ConnectionHelper {
    private String propertyFile;

    public MySQLConnectionHelper() {
        super();
        propertyFile="db";
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

    private String buildUrl(){
        String url="";
        return  url;
    }

    private ResourceBundle getBundle(){
        ResourceBundle rb = ResourceBundle.getBundle(propertyFile);
        return rb;
    }

}
