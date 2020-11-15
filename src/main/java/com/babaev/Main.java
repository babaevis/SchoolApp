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

class Main {
    private static final String SQL_SCRIPT = "src/main/resources/tables.sql";
    private static final String PROPERTIES = "src/main/resources/db.properties";

    public static void main(String[] args) {
        Connection con = ConnectionProvider.getConnection(new File(PROPERTIES));
        SqlScriptRunner.runScript(new File(SQL_SCRIPT), con);

        List<Group> groups = GroupDataGenerator.generateGroups();
        CrudDao<Group, Long> groupDao = new GroupDaoImpl(con);
        groups.forEach(groupDao::save);

        UserInterface ui = new UserInterface(con);
        ui.runInterface();
    }
}
