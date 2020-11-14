package com.babaev.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {
    private static String url;
    private static String user;
    private static String password;
    private static final String PROPERTY_PATH = "src/main/resources/db.properties";

    private ConnectionProvider(){}

    public static Connection getConnection(){
        Connection con = null;
        loadProperty();
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    private static void loadProperty(){
        try (InputStream input = new FileInputStream(PROPERTY_PATH)) {
            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
