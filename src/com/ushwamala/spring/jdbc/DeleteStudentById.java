package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class DeleteStudentById {
    public static void main(String[] args) {

        //Retrieve the student from the database using the ID
        System.out.println("Enter the student id: ");
        int studentId = new Scanner(System.in).nextInt();

        //delete the student assigned to entered student id
        deleteStudentById(studentId);

    }

    private static void deleteStudentById(int studentId){
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            //start transaction
            session.beginTransaction();

            //Retrieve student using the given id
            Student tempStudent = session.get(Student.class, studentId);

            try {
                //delete course
                System.out.println("Deleting student: " + tempStudent);
                session.delete(tempStudent);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

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
