package com.babaev;

import com.babaev.service.ConnectionProvider;
import com.babaev.service.SqlScriptRunner;

import java.io.File;
import java.sql.Connection;

/**
 * @author Islam Babaev
 */
public class Main {

    public static void main(String[] args) {
        Connection con = ConnectionProvider.openConnection();
        makeInitialSetup(con);

        UserInterface uI = new UserInterface(con);
        uI.runInterface();
    }

    private static void makeInitialSetup(Connection con){
        File tables = new File("src/main/resources/tables.sql");
        SqlScriptRunner sqlScriptRunner = new SqlScriptRunner(tables, con);

        sqlScriptRunner.runScript();
    }
}

