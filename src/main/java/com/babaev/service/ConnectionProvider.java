package com.babaev.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private ConnectionProvider(){}

    public static Connection getConnection(String url, String username, String password){
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }
}
