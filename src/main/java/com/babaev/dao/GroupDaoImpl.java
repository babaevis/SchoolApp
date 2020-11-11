package com.babaev.dao;

import com.babaev.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Islam Babaev
 */
public class GroupDaoImpl implements CrudDao<Group, Integer> {
    private final Connection con;
    private PreparedStatement statement;
    private ResultSet rs;

    public GroupDaoImpl(Connection con) {
        this.con = con;
    }


    @Override
    public Optional<Group> findById(Integer id) {
        String query = "SELECT * FROM groups WHERE group_id = ?";

        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if(rs.next()){
                Group group = new Group();
                group.setId(rs.getInt("group_id"));

                return Optional.of(group);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void save(Group group) {
        String query = "INSERT INTO groups (group_name) VALUES (?)";

        try {
            statement = con.prepareStatement(query);
            statement.setString(1, group.getName());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Group group) {
        String query = "UPDATE groups SET group_name = ? WHERE group_id = ?";

        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, (int)group.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM groups WHERE group_id = ?";

        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteAllByIds(Set<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public List<Group> findAll() {
        String select = "SELECT * FROM students";
        List<Group> groups = new ArrayList<>();

        try {
            statement = con.prepareStatement(select);
            rs = statement.executeQuery();

            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("group_id"));
                group.setName(rs.getString("group_name"));
                groups.add(group);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return groups;
    }
}
