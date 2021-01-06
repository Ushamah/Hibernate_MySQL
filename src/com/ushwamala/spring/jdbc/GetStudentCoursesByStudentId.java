package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;


public class GetStudentCoursesByStudentId {
    public static void main(String[] args) {
        System.out.println("Enter the course id: ");
        getStudentCoursesByStudentId(new Scanner(System.in).nextInt());
    }

    private static void getStudentCoursesByStudentId(int studentId) {
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
            System.out.println("Student: " + tempStudent);

            //Retrieve the list of courses assigned to the student
            System.out.println("Courses: " + tempStudent.getCourses() + "\n");

            //save tempStudent and leverage the cascade
            session.save(tempStudent);

            //commit the transaction
            session.getTransaction().commit();
            System.out.println("Done!");

        } catch (Exception e) {
            System.out.println("Entered student id doesn't exist!");
            e.printStackTrace();

        } finally {
            sessionFactory.close();
        }
    }
}
