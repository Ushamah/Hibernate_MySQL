package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInstructor {
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
                .buildSessionFactory(); Session session = sessionFactory.getCurrentSession();

        try {
            //start transaction
            session.beginTransaction();

            //Create Instructor and InstructorDetail
            Instructor tempInstructor = new Instructor("Johan","Baum","baumjohan@gmail.com");
            InstructorDetail tempInstructorDetail =
                    new InstructorDetail("http://www.baumjohan/youtube","Skating");

           // associate instructorDetail to instructor
            tempInstructor.setInstructorDetail(tempInstructorDetail);

            //save the tempInstructor
            //Note that this will also save the tempInstructorDetail because of the CascadeType=ALL
            System.out.println("Saving instructor: " + tempInstructor);
            session.save(tempInstructor);

            //commit the transaction
            session.getTransaction().commit();
            System.out.println("Done!");

        } catch (Exception e) {
            e.printStackTrace();

        } finally{
            sessionFactory.close();
        }
    }
}
