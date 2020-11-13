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
    private PreparedStatement statement;
    private ResultSet rs;
    private final Connection con;
    private final String FIND_QUERY = "SELECT * FROM groups WHERE group_id = ?";
    private final String UPDATE_QUERY = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private final String SAVE_QUERY = "INSERT INTO groups (group_name) VALUES (?)";
    private final String DELETE_QUERY = "DELETE FROM groups WHERE group_id = ?";
    private final String FINDALL_QUERY = "SELECT * FROM groups";

    public GroupDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Optional<Group> findById(Integer id) {
        try {
            statement = con.prepareStatement(FIND_QUERY);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if(rs.next()){
                Group group = new Group();
                group.setId(rs.getInt("group_id"));
                group.setName(rs.getString("group_name"));

                return Optional.of(group);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void save(Group group) {
        try {
            statement = con.prepareStatement(SAVE_QUERY);
            statement.setString(1, group.getName());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Group group) {
        try {
            statement = con.prepareStatement(UPDATE_QUERY);
            statement.setString(1, group.getName());
            statement.setInt(2, (int)group.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            statement = con.prepareStatement(DELETE_QUERY);
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
        List<Group> groups = new ArrayList<>();
        try {
            statement = con.prepareStatement(FINDALL_QUERY);
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
