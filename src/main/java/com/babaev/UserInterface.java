package com.babaev;

import com.babaev.dao.CrudDao;
import com.babaev.dao.StudentDaoImpl;
import com.babaev.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Islam Babaev
 */
public class UserInterface {
    private final Scanner scanner;
    private final Connection con;

    public UserInterface(Connection con) {
        this.con = con;
        scanner = new Scanner(System.in);
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
        System.out.println("\n                     MAIN MENU\n=====================================================");
        System.out.println("1.Show all students");
        System.out.println("2.Add new student");
        System.out.println("3.Delete student by id");
        System.out.println("0.EXIT");
        System.out.println("--> Enter 1-4 to select operation. '0' to exit <--");
    }

    private void findStudents() {
        StudentDaoImpl studentDao = new StudentDaoImpl(con);
        List<Student> students = studentDao.findAll();
        printStudents(students);
    }

    private void addNewStudent() {
        Student student = new Student();
        CrudDao studentDao = new StudentDaoImpl(con);

        System.out.println("Enter first name of new student:");
        String firstName = scanName();
        System.out.println("Enter last name of new student:");
        String lastName = scanName();
        System.out.println("Enter patronimyc of new student");
        String patronimyc = scanName();

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPatronimyc(patronimyc);
        studentDao.save(student);
        System.out.println("-------------------------");
        System.out.println(lastName + " " + firstName + " " + patronimyc + " added");
        System.out.println("-------------------------");
    }

    private void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("--------------------------");
            System.out.println("       No students");
            System.out.println("--------------------------");

        } else {
            System.out.println("---------------------------------");
            System.out.println("Firstname | Lastname | Patronymic");
            System.out.println("---------------------------------");

            for (int i = 0; i < students.size(); i++) {
                System.out.print(i + 1 + ". " + students.get(i).getFirstName() + "   ");
                System.out.print(students.get(i).getLastName()+ "   ");
                System.out.println(students.get(i).getPatronimyc());
            }
        }
    }
    private void deleteStudentById(){
            System.out.println("Enter the id of the student:");
            int id = scanNumber();

            StudentDaoImpl studentDao = new StudentDaoImpl(con);
            studentDao.deleteById(id);

            System.out.println("Student successfully deleted\n----------------------------");
        }
    private String scanName(){
        return scanner.next();
    }
    private int scanNumber(){
        int input = -1;

        while(input < 0){
            input = scanner.nextInt();

            if(input < 0 ){
                System.out.println("Id should be positive. Try again...");
            }
        }

        return input;
    }
}