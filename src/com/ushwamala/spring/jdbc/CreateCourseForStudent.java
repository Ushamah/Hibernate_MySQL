package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;


public class CreateCourseForStudent {
    public static void main(String[] args) {
      /*  String jdbcUrl = "jdbc:mysql://localhost:3307/hb-03-one-to-many?useSSL=false";
        String user = "hbstudent";
        String password = "hbstudent";
        try {
            System.out.println("Connecting to database: " + jdbcUrl);
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Connected successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //Create new course and assign it to a desired student
        Course newCourse = new Course("Introduction To Cloud Computing");
        System.out.println("Enter the student's id: ");
        createCourseForStudent(new Scanner(System.in).nextInt(), newCourse);

    }

    private static void createCourseForStudent(int studentId, Course course) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            //start transaction
            session.beginTransaction();

            //Retrieve the student from the database using the ID
            Student tempStudent = session.get(Student.class, studentId);

            //add the student to the  course student
            course.addStudent(tempStudent);

            //save tempStudent and leverage the cascade
            session.save(course);

            //commit the transaction
            session.getTransaction().commit();
            System.out.println("Done!");

        } catch (Exception e) {
            System.out.println("Entered course id doesn't exist!");
            e.printStackTrace();

        } finally {
            sessionFactory.close();
        }
    }
}
