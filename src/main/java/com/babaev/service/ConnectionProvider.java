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

    private ConnectionProvider(){}

    public static Connection getConnection(String path){
        Connection con = null;
        loadProperty(path);
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    private static void loadProperty(String path){
        try (InputStream input = new FileInputStream(path)) {
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
