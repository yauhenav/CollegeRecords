package com.yauhenav.logic.dao;

import java.util.*;

import com.yauhenav.logic.dto.*;
import com.yauhenav.logic.exception.*;

public interface SubjectDao {

    /**
     * Create a new DB entry as per corresponding received object
     */
    void create(Subject subject) throws DaoException;

    /**
     * Return the object corresponding to the DB entry as per received object with specified id
     */
    Subject read(Subject subject) throws DaoException;

    /**
     * Modify the DB entry as per corresponding received object
     */
    void update(Subject subject) throws DaoException;

    /**
     * Remove the DB entry as per corresponding received object
     */
    void delete(Subject subject)  throws DaoException;

    /**
     * Return a list of objects corresponding to all DB entries
     */
    List<Subject> getAll() throws DaoException;

    /**
     * Terminate the connection and all 'PreparedStatements
     */
    void close () throws DaoException;
}
