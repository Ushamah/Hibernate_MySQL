package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateCourse {
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

            //Retrieve the instructor from the database using the ID
            System.out.println("Enter the instructor id: ");
            int id = new Scanner(System.in).nextInt();
            Instructor tempInstructor = session.get(Instructor.class, id);

            //Create new courses list
            Course[] coursesArray = {
                    new Course("Java For Beginners"),
                    new Course("Angular For Web Developers"),
                    new Course("PHP For Intermediates")
            };
            List<Course> coursesList = Arrays.asList(coursesArray);

            //add courses in the list to the instructor
            tempInstructor.addCourse(coursesList);

            //save the courses to the database
            saveCoursesToDB(coursesList,session);

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

    private static void saveCoursesToDB(List<Course> courses, Session session){
        courses.forEach(session::save);
    }
}
