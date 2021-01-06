package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class DeleteCourse {
    public static void main(String[] args) {

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

            //Retrieve the course from the database using the ID
            System.out.println("Enter the course id: ");
            int id = new Scanner(System.in).nextInt();
            Course tempCourse = session.get(Course.class, id);

            try {
                //delete course
                System.out.println("Deleting course: " + tempCourse);
                session.delete(tempCourse);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            //commit the transaction
            session.getTransaction().commit();
            System.out.println("Done!");

        } catch (Exception e) {
            System.out.println("Entered instructor id doesn't exist!");
            e.printStackTrace();

        } finally {
            sessionFactory.close();
        }
    }
}
