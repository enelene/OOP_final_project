package com.example.quizwebsite.listeners;

import org.apache.commons.dbcp2.BasicDataSource;
import com.example.quizwebsite.userManager.UserManager;
import com.example.quizwebsite.userManager.RelationManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class DatabaseContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver class
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver for MySQL", e);
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/quizwebsite");
        dataSource.setUsername("root");
        dataSource.setPassword("Elene2004!"); // Remember to change this

        sce.getServletContext().setAttribute("dataSource", dataSource);

        UserManager userManager = new UserManager(dataSource);
        sce.getServletContext().setAttribute("userManager", userManager);

        RelationManager relationManager = new RelationManager(dataSource);
        sce.getServletContext().setAttribute("relationManager", relationManager);
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