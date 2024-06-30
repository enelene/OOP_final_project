package com.example.quizwebsite.quizManager;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;


@WebListener
public class DatabaseContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource dataSource = new BasicDataSource();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver class
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver for MySQL", e);
        }
        dataSource.setUrl("jdbc:mysql://localhost:3306/quizwebsite");
        dataSource.setUsername("root");
        dataSource.setPassword("Elene2004!");

        sce.getServletContext().setAttribute("dataSource", dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        BasicDataSource dataSource = (BasicDataSource) sce.getServletContext().getAttribute("dataSource");
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
