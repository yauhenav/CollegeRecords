package com.yauhenav.web;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import com.yauhenav.logic.service.*;
import com.yauhenav.logic.exception.ServiceException;

/**
 * Created by yauhenav on 9.5.17.
 */
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent e) {
        try {
            HttpSession sessObj = e.getSession();
            Service sesMngObj = new Service();
            sessObj.setAttribute("sessionObject", sesMngObj);
        } catch (ServiceException exc) {
            exc.printStackTrace();
        }
    }
    public void sessionDestroyed(HttpSessionEvent e) {
        try {
            HttpSession sessObj = e.getSession();
            Service sesMngObj = (Service) sessObj.getAttribute("sessionObject");
            sesMngObj.close();
            sessObj.removeAttribute("sessionObject");
        } catch (ServiceException exc) {
            exc.printStackTrace();
        }
    }
}
