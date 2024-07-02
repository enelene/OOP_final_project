//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class
AppContextListener implements ServletContextListener {
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
        dataSource.setPassword("Elene2004!"); //dont forget to change


        UserManager userManager = new UserManager(dataSource);
        sce.getServletContext().setAttribute("userManager", userManager);
        RelationManager relationManager = new RelationManager(dataSource);
        sce.getServletContext().setAttribute("relationManager", relationManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        BasicDataSource dataSource = (BasicDataSource) sce.getServletContext().getAttribute("dataSource");
        try {
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
