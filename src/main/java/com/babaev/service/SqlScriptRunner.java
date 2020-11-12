package com.babaev.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Islam Babaev
 */
public class SqlScriptRunner {
    private final Connection con;
    private final File script;

    public SqlScriptRunner(File script, Connection con) {
        this.script = script;
        this.con = con;
    }

    public void runScript() {
        if(!script.exists()) {
            throw new IllegalArgumentException("File not found");
        }
        if(script.length() == 0) {
            throw new IllegalArgumentException("File is empty");
        }
        if(con == null){
            throw new IllegalArgumentException("Bad connection");
        }

        BufferedReader br;
        String line;

        try (FileReader fr = new FileReader(script)){
            br = new BufferedReader(fr);

            line = br.readLine();

            while (line != null) {
                executeQuery(line);
                line = br.readLine();
            }

            br.close();
        } catch (IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void executeQuery(String query){
        Statement statement = null;

        try {
            statement = con.createStatement();
            statement.execute(query);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
