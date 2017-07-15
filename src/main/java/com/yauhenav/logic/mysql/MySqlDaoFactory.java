package com.yauhenav.logic.mysql;

import java.sql.*;
import java.util.*;
import java.io.*;

import com.yauhenav.logic.dao.*;
import com.yauhenav.logic.exception.*;

public class MySqlDaoFactory implements DaoFactory {

    private Connection connection = null;

    // Constructor
    public MySqlDaoFactory() throws DaoException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties props = new Properties();
            InputStream stream = this.getClass().getResourceAsStream("/config.properties");
            props.load(stream);
            this.connection =  DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
        } catch (SQLException exc) {
            throw new DaoException("Exception for DAO", exc);
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public StudentDao getStudentDao() throws DaoException {
        return new MySqlStudentDao(connection);
    }

    @Override
    public SubjectDao getSubjectDao() throws DaoException {
        return new MySqlSubjectDao(connection);
    }

    @Override
    public MarkDao getMarkDao() throws DaoException {
        return new MySqlMarkDao(connection);
    }

    // Close Connection instance object
    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exc) {
                throw new DaoException("Exception for DAO", exc);
            }
        } else {
            System.err.println("Connection was not established");
        }
    }
}
