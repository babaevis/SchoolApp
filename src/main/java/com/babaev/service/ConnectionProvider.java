package com.babaev.service;

import java.io.File;
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

    public static Connection getConnection(File file){
        if(!file.exists())
            throw new IllegalArgumentException("Invalid property path");
        if(file.length() == 0)
            throw new IllegalArgumentException("Property is empty");

        Connection con = null;
        loadProperty(file);
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    private static void loadProperty(File file){
        try (InputStream input = new FileInputStream(file)) {
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
