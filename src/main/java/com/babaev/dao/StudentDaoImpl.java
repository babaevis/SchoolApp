package com.babaev.dao;

import com.babaev.model.Group;
import com.babaev.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

/**
 * @author Islam Babaev
 */
public class StudentDaoImpl implements CrudDao<Student, Integer> {
    private final Connection con;
    private PreparedStatement statement;
    private ResultSet rs;

    public StudentDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if (rs.next()){
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String patronimyc = rs.getString("patronimyc");
                Date birthdate = rs.getDate("birth_date");
                long groupId = rs.getLong("group_id");

                Student student = new Student(id, firstname, lastname, patronimyc, birthdate);
                Group group = new Group(groupId);
                group.addStudent(student);
                student.setGroup(group);

                return Optional.of(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Student student) {
        String query = "INSERT INTO students (first_name, last_name, patronymic, birth_date, group_id) VALUES (?, ?, ?, ?, ?)";

        try {
            statement = con.prepareStatement(query);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getPatronimyc());
            statement.setDate(4, student.getBirthdate());
            statement.setInt(5, (int)student.getGroup().getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void update(Student student) {
        String query = "UPDATE student SET first_name = ?, last_name = ?, pantronimyc = ?, birthdate = ?, group_id = ?";

        try {
            statement = con.prepareStatement(query);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getPatronimyc());
            statement.setDate(4, student.getBirthdate());
            statement.setInt(5, (int)student.getGroup().getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM students WHERE student_id = ?";

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
    public List<Student> findAll(){
        String query = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try {
            statement = con.prepareStatement(query);
            rs = statement.executeQuery();

            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setBirthdate(rs.getDate("birth_date"));
                student.setPatronimyc(rs.getString("patronymic"));
                CrudDao<Group, Integer> groupDao = new GroupDaoImpl(con);
                Optional<Group> group = groupDao.findById(rs.getInt("group_id"));
                group.ifPresent(student::setGroup);
                students.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return students;
    }
}
