package com.babaev.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Islam Babaev
 */
public class Group {
    private long id;
    private String name;
    private List<Student> students;

    public Group() {
    }

    public Group(long id) {
        this.id = id;
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addStudent(Student student){
        students.add(student);
    }
}
