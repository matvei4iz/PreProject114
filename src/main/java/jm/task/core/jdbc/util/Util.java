package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Root1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к базе данных");
        }
        return connection;
    }

    private static SessionFactory concreteSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            Properties prop = new Properties();
            prop.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            prop.put(Environment.URL, "jdbc:mysql://localhost:3306/database");
            prop.put(Environment.USER, "root");
            prop.put(Environment.PASS, "Root1234");
            prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL57Dialect");

            prop.put(Environment.SHOW_SQL, "true");

            prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            prop.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(prop);

            configuration.addAnnotatedClass(User.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            concreteSessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }
}
