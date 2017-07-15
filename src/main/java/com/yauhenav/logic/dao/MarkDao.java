package com.yauhenav.logic.dao;

import java.util.*;

import com.yauhenav.logic.dto.*;
import com.yauhenav.logic.exception.*;

public interface MarkDao {

    // Create a new DB entry as per corresponding received object
    void create(Mark mark) throws DaoException;

    // Return the object corresponding to the DB entry with received primary 'key'
    Mark read(Mark mark) throws DaoException;

    // Modify the DB entry as per corresponding received object
    void update(Mark mark) throws DaoException;

    // Remove the DB entry as per corresponding received object
    void delete(Mark mark) throws DaoException;

    // Return a list of objects corresponding to all DB entries
    List<Mark> getAll() throws DaoException;

    // Return a list of Marks of one Student as per received primary 'key'
    List<Mark> getAllMarkOneStud (Mark mark) throws DaoException;

    // Terminate the connection and all 'PreparedStatement's
    void close() throws DaoException;
}
