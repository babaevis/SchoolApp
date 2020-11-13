package com.babaev;

import com.babaev.dao.CrudDao;
import com.babaev.dao.StudentDaoImpl;
import com.babaev.model.Group;
import com.babaev.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

/**
 * @author Islam Babaev
 */
public class UserInterface {
    private final Scanner scanner;
    private final Connection con;

    public UserInterface(Connection con) {
        this.con = con;
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
        out.println("0.EXIT");
        out.println("--> Enter 1-4 to select operation. '0' to exit <--");
    }

    private void findStudents() {
        StudentDaoImpl studentDao = new StudentDaoImpl(con);
        List<Student> students = studentDao.findAll();
        printStudents(students);
    }

    private void addNewStudent() {
        Student student = new Student();
        CrudDao<Student, Integer> studentDao = new StudentDaoImpl(con);

        out.println("Enter first name of new student:");
        String firstName = scanner.next();
        out.println("Enter last name of new student:");
        String lastName = scanner.next();
        out.println("Enter patronimyc of new student");
        String patronimyc = scanner.next();
        out.println("Enter group id of new student");
        int grouId = scanner.nextInt();

        Group group = new Group();
        group.setId(grouId);

        student.setGroup(group);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPatronimyc(patronimyc);
        studentDao.save(student);

        out.println("-------------------------");
        out.println(lastName + " " + firstName + " " + patronimyc + " added");
        out.println("-------------------------");
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
        result.append(String.format("%-25s", "Last Name"));
        result.append(String.format("%-25s", "First Name"));
        result.append(String.format("%-25s", "Patronymic"));
        result.append("\n");

            students.forEach( student -> {
                result.append(String.format("%-5s", student.getId()));
                result.append(String.format("%-10s", student.getGroup().getId()));
                result.append(String.format("%-25s", student.getLastName()));
                result.append(String.format("%-25s", student.getFirstName()));
                result.append(String.format("%-25s", student.getPatronimyc()));
                result.append("\n");
            });
            out.println(result.toString());
    }

    private void deleteStudentById(){
            out.println("Enter id of the student:");
            int id = scanner.nextInt();

            StudentDaoImpl studentDao = new StudentDaoImpl(con);
            studentDao.deleteById(id);

            out.println("Student successfully deleted\n----------------------------");
    }
}