package com.example.quizwebsite.listeners;
import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Application Lifecycle Listener implementation class RequestListener
 *
 */
@WebListener
public class RequestListener implements ServletRequestListener, ServletRequestAttributeListener {

    /**
     * Default constructor.
     */
    public RequestListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0) {
        // Persistent Cookies
        HttpServletRequest req = (HttpServletRequest) arg0.getServletRequest();
        Cookie[] cookies = req.getCookies();

        if(cookies != null) {
            for (int i = 0; i < cookies.length; i++){
                Cookie cookie = cookies[i];
                if(cookie.getName().equals("user_key")) {
                    // todo - maybe make all usermanager functions static
                    User u = UserManager.getUserByCookieKey(cookie.getValue());
                    if(u != null) {
                        HttpSession sess = req.getSession();
                        sess.setAttribute("user", u);
                    }
                }
            }
        }
    }

}
