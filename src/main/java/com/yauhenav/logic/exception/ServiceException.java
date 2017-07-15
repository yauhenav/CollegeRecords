package com.yauhenav.logic.exception;

/**
 * Created by yauhenav on 18.5.17.
 */
public class ServiceException extends Exception {
    public ServiceException () {
    }

    public ServiceException (String message, Throwable exc) {
        super (message, exc);
    }

    public ServiceException (String message) {
        super(message);
    }

    public ServiceException (Throwable exc) {
        super(exc);
    }

    public String toString() {
        return "ServiceException";
    }
}
