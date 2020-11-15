package com.babaev.dao;

import com.babaev.model.Group;
import com.babaev.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

public class StudentDaoImpl implements CrudDao<Student, Long> {
    private PreparedStatement statement;
    private ResultSet rs;
    private final Connection con;
    private final String FIND_QUERY = "SELECT * FROM students WHERE student_id = ?";
    private final String UPDATE_QUERY = "UPDATE students SET first_name = ?, last_name = ?, patronymic = ?, birth_date = ?, group_id = ? WHERE student_id = ?";
    private final String SAVE_QUERY = "INSERT INTO students (first_name, last_name, patronymic, birth_date, group_id) VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_QUERY = "DELETE FROM students WHERE student_id = ?";
    private final String FINDALL_QUERY = "SELECT * FROM students";

    public StudentDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Optional<Student> findById(Long id) {
        try {
            statement = con.prepareStatement(FIND_QUERY);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()){
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String patronimyc = rs.getString("patronimyc");
                Date birthdate = rs.getDate("birth_date");
                long groupId = rs.getLong("group_id");

                Student student = new Student(id, firstname, lastname, patronimyc, birthdate);
                CrudDao<Group, Long> groupDao = new GroupDaoImpl(con);
                Optional<Group> group = groupDao.findById(groupId);
                group.ifPresent(student::setGroup);

                return Optional.of(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(Student student) {
        try {
            statement = con.prepareStatement(SAVE_QUERY);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getPatronymic());
            statement.setDate(4, student.getBirthdate());
            statement.setLong(5, student.getGroup().getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void update(Student student) {
        try {
            statement = con.prepareStatement(UPDATE_QUERY);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getPatronymic());
            statement.setDate(4, student.getBirthdate());
            statement.setLong(5, student.getGroup().getId());
            statement.setLong(6, student.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            statement = con.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deleteAllByIds(Set<Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public List<Student> findAll(){
        List<Student> students = new ArrayList<>();
        try {
            statement = con.prepareStatement(FINDALL_QUERY);
            rs = statement.executeQuery();

            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setBirthdate(rs.getDate("birth_date"));
                student.setPatronymic(rs.getString("patronymic"));
                CrudDao<Group, Long> groupDao = new GroupDaoImpl(con);
                Optional<Group> group = groupDao.findById(rs.getLong("group_id"));
                group.ifPresent(student::setGroup);
                students.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return students;
    }
}
