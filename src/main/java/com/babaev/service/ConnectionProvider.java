package com.babaev.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Islam Babaev
 */
public class ConnectionProvider {

    private ConnectionProvider(){}

    public static Connection getConnection(){
        String url = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "123";
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }
}
