package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.Course;
import com.ushwamala.spring.hibernate.entities.Instructor;
import com.ushwamala.spring.hibernate.entities.InstructorDetail;
import com.ushwamala.spring.hibernate.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class GetInstructorCourses {
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
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            //start transaction
            session.beginTransaction();

            //Retrieve the instructor from the database using the ID
            int id = 1;
            Instructor tempInstructor = session.get(Instructor.class, id);
            System.out.println("Instructor: " + tempInstructor);

           //Retrieve the courses of the instructor
            System.out.println("Courses: " + tempInstructor.getCourses());

            //commit the transaction
            session.getTransaction().commit();
            System.out.println("Done!");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            sessionFactory.close();
        }
    }

    private static void saveCourses(List<Course> courses, Session session){
        courses.forEach(session::save);
    }


}
