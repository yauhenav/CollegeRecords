package com.yauhenav.logic.mysql;

import java.sql.*;
import java.util.*;
import java.io.*;

import com.yauhenav.logic.dao.*;
import com.yauhenav.logic.exception.*;

public class MySqlDaoFactory implements DaoFactory {

    public Connection connection = null;
    private String selectDataBase = null;


    // Constructor
    public MySqlDaoFactory(String selectDataBase) throws DaoException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties props = new Properties();
            this.selectDataBase = selectDataBase;
            InputStream stream = this.getClass().getResourceAsStream(selectDataBase);
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
        return new MySqlStudentDao(connection, selectDataBase);
    }

    @Override
    public SubjectDao getSubjectDao() throws DaoException {
        return new MySqlSubjectDao(connection, selectDataBase);
    }

    @Override
    public MarkDao getMarkDao() throws DaoException {
        return new MySqlMarkDao(connection, selectDataBase);
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
