package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CreateCourseReviews {
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

        //Create new reviews list
        Review[] reviewsArray = {
                new Review("Great stuff..loved it"),
                new Review("Excellent instructor!"),
                new Review("Boring")
        };
        List<Review> reviews = Arrays.asList(reviewsArray);
        System.out.println("Enter the course id: ");
        createReviewsForCourse(new Scanner(System.in).nextInt(), reviews);


    }

    private static void createReviewsForCourse(int courseId, List<Review> reviews) {
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

            //Retrieve the course from the database using the ID
            Course tempCourse = session.get(Course.class, courseId);

            //add reviews to the  created course course
            tempCourse.addReviews(reviews);

            //save tempCourse and leverage the cascade
            session.save(tempCourse);

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
