package com.babaev.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlScriptRunner {
    private SqlScriptRunner(){
    }

    public static void runScript(File script, Connection connection) {
        if(!script.exists()) {
            throw new IllegalArgumentException("File not found");
        }
        if(script.length() == 0) {
            throw new IllegalArgumentException("File is empty");
        }
        if(connection == null){
            throw new IllegalArgumentException("Bad connection");
        }

        BufferedReader br;
        try (FileReader fr = new FileReader(script)){
            br = new BufferedReader(fr);
            String line = br.readLine();
            Statement statement = connection.createStatement();
            while (line != null) {
                statement.execute(line);
                line = br.readLine();
            }
            br.close();
            statement.close();
        } catch (IOException | SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
