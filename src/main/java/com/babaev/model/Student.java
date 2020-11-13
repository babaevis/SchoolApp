package com.babaev.model;


import java.sql.Date;

public class Student {
    private long id;
    private String firstName;
    private String lastName;
    private String patronimyc;
    private Group group;
    private Date birthdate;

    public Student() {
    }

    public Student(long id, String firstName, String lastName, String patronimyc, Date birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronimyc = patronimyc;
        this.birthdate = birthdate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronimyc() {
        return patronimyc;
    }

    public void setPatronimyc(String patronimyc) {
        this.patronimyc = patronimyc;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
