package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp.1.1.4";
    private static final String DB_USERNAME = "Utkativ";
    private static final String DB_PASSWORD = "root";

    private static SessionFactory sessionFactory;

    public static Connection getConnectionBD() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            return connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            synchronized (Util.class){
                if(sessionFactory == null){
                    return sessionFactory = new Configuration()
                            .setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/pp.1.1.4")
                            .setProperty("hibernate.connection.username","Utkativ")
                            .setProperty("hibernate.connection.password","root")
                            .setProperty("show_sql, true","create")
                            .setProperty("hibernate.current_session_context_class","thread")
                            .setProperty("hibernate.current_session_context_class","thread")
                            .setProperty("hibernate.connection.autocommit","true")
                            .addAnnotatedClass(User.class).
                            buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

}

