//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    public AppContextListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        AccountManager accountManager = new AccountManager();
        sce.getServletContext().setAttribute("accountManager", accountManager);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
