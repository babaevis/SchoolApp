package com.babaev;

import com.babaev.dao.CrudDao;
import com.babaev.dao.GroupDaoImpl;
import com.babaev.dao.StudentDaoImpl;
import com.babaev.model.Group;
import com.babaev.model.Student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

class UserInterface {
    private final Scanner scanner;
    private final Connection con;

    public UserInterface(Connection connection){
        con = connection;
        scanner = new Scanner(in);
    }

    public void runInterface() {
        boolean exit = false;
        while (!exit) {
            printMenu();

            switch (scanner.next()) {
                case "0":
                    exit = true;
                    break;
                case "1":
                    findStudents();
                    break;
                case "2":
                    addNewStudent();
                    break;
                case "3":
                    deleteStudentById();
                    break;
                case "4":
                    findGroups();
                    break;
                default:
                    break;
            }
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void printMenu() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("\n\n\n                     MAIN MENU\n" +
                            "=====================================================");
        out.println("1.Show all students");
        out.println("2.Add new student");
        out.println("3.Delete student by id");
        out.println("4.Show groups");
        out.println("0.EXIT");
        out.println("--> Enter 1-4 to select operation. '0' to exit <--");
    }

    private void findStudents() {
        CrudDao<Student, Long> studentDao = new StudentDaoImpl(con);
        List<Student> students = studentDao.findAll();
        printStudents(students);
    }

    private void findGroups(){
        CrudDao<Group, Long> groupDao = new GroupDaoImpl(con);
        List<Group> groups = groupDao.findAll();
        printGroups(groups);
    }

    private void printGroups(List<Group> groups){
        StringBuilder result = new StringBuilder();
        result.append(" Id").append("        Group");
        result.append("\n");
        groups.forEach(group -> {
            result.append(String.format("%2s", group.getId()));
            result.append(String.format("%14s", group.getName()));
            result.append("\n");
        });
        out.println(result);
    }

    private void addNewStudent() {
        CrudDao<Student, Long> studentDao = new StudentDaoImpl(con);

        out.println("Enter first name of new student:");
        String firstName = scanner.next();
        out.println("Enter last name of new student:");
        String lastName = scanner.next();
        out.println("Enter patronymic of new student");
        String patronymic = scanner.next();

        String groupId = null;
        while (!(isValid(groupId) && Integer.parseInt(groupId) <= 10)){
            out.println("Enter group id of new student");
            groupId = scanner.next();
        }

        out.println("Enter birthdate of new student in dd/MM/yyyy format");
        java.util.Date utilDate = null;
        try {
            utilDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next());
        } catch (ParseException e) {
            out.println("\n\n-------->Invalid date format<-------");
            e.printStackTrace();
        }
        Date birthdate = new java.sql.Date(utilDate.getTime());
        Group group = new Group();
        group.setId(Integer.parseInt(groupId));
        Student student = new Student(firstName, lastName, patronymic, birthdate,group);
        studentDao.save(student);

        out.println("-----------------------------------");
        out.println(lastName + " " + firstName + " " + patronymic + " " + birthdate);
        out.println("-----------------------------------");
    }

    private void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            out.println("--------------------------");
            out.println("       No students");
            out.println("--------------------------");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("%-5s", "Id"));
        result.append(String.format("%-10s", "Group Id"));
        result.append(String.format("%-20s", "Last Name"));
        result.append(String.format("%-20s", "First Name"));
        result.append(String.format("%-25s", "Patronymic"));
        result.append(String.format("%-10s", "Birthdate"));
        result.append("\n");

        students.forEach( student -> {
            result.append(String.format("%-5s", student.getId()));
            result.append(String.format("%-10s", student.getGroup().getId()));
            result.append(String.format("%-20s", student.getLastName()));
            result.append(String.format("%-20s", student.getFirstName()));
            result.append(String.format("%-25s", student.getPatronymic()));
            result.append(String.format("%-10s", student.getBirthdate()));
            result.append("\n");
        });
        out.println(result.toString());
    }

    private void deleteStudentById(){
        String userAnswer = null;
        while(!(isValid(userAnswer))){
            out.println("Enter id of the student:");
            userAnswer = scanner.next();
        }
        CrudDao<Student, Long> studentDao = new StudentDaoImpl(con);
        studentDao.deleteById(Long.parseLong(userAnswer));

        out.println("Student #" + userAnswer + " successfully deleted\n----------------------------");
    }

    private boolean isValid(String str){
        if(str == null)
            return false;
        for(int i = 0; i < str.length(); i++){
            if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9'))
                return false;
        }
        return Integer.parseInt(str) > 0;
    }
}