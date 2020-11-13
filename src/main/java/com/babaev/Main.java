package com.babaev;

import com.babaev.dao.CrudDao;
import com.babaev.dao.GroupDaoImpl;
import com.babaev.model.Group;
import com.babaev.service.ConnectionProvider;
import com.babaev.service.GroupDataGenerator;
import com.babaev.service.SqlScriptRunner;

import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * @author Islam Babaev
 */
public class Main {
private static final String SCRIPT = "src/main/resources/tables.sql";

    public static void main(String[] args) {
        Connection con = ConnectionProvider.getConnection();
        makeInitialSetup(con);

        UserInterface uI = new UserInterface(con);
        uI.runInterface();
    }

    private static void makeInitialSetup(Connection con){
        File tables = new File(SCRIPT);
        SqlScriptRunner sqlScriptRunner = new SqlScriptRunner(tables, con);

        sqlScriptRunner.runScript();
        List<Group> groups = GroupDataGenerator.generateGroups();
        saveGroups(groups, con);
    }

    private static void saveGroups(List<Group> groups, Connection con){
        CrudDao<Group, Integer> groupDao = new GroupDaoImpl(con);
        groups.forEach(groupDao::save);
    }
}

