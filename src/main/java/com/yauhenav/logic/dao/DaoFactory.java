package com.yauhenav.logic.dao;

import com.yauhenav.logic.exception.*;

public interface DaoFactory {

    /**
     * Return an object to manage persistent 'Student' object
     */
    StudentDao getStudentDao() throws DaoException;

    /**
     * Return an object to manage persistent 'Subject' object
     */
    SubjectDao getSubjectDao() throws DaoException;

    /**
     * Return an object to manage persistent 'Mark' object
     */
    MarkDao getMarkDao () throws DaoException;

    /**
     * Close Connection instance object
     */
    void close() throws DaoException;
}
