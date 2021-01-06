package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;


public class AddExistingCourseToExistingStudent {
    public static void main(String[] args) {

        //Retrieve the course using the courseId
        System.out.println("Enter the course's id: ");
        int courseId = new Scanner(System.in).nextInt();

        //Retrieve the course using the courseId
        System.out.println("Enter the student's id: ");
        int studentId = new Scanner(System.in).nextInt();
        addStudentToCourse(courseId, studentId);

    }

    private static void addStudentToCourse(int courseId, int studentId) {
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

            //Retrieve the course and student from the database using their respective ID
            Course tempCourse = session.get(Course.class, courseId);
            Student tempStudent = session.get(Student.class, studentId);

            //add the course to the student
            tempStudent.addCourse(tempCourse);

            //save tempStudent and leverage the cascade
            session.save(tempStudent);

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
