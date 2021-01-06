package com.ushwamala.spring.jdbc;

import com.ushwamala.spring.hibernate.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudent {
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

            //Create new students
            Student ush = new Student("Ushamah","Wamala","ushwamala@yahoo.com");
            Student ref = new Student("Refia","Yurtay","refyurtay@yahoo.com");


            //save the students
            session.save(ush);
            session.save(ref);

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
