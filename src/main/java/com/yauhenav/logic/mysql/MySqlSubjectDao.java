package com.yauhenav.logic.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import com.yauhenav.logic.dao.*;
import com.yauhenav.logic.dto.*;
import com.yauhenav.logic.exception.*;

public class MySqlSubjectDao implements SubjectDao {

    private String SQL_CREATE_SUBJ = null;
    private String SQL_READ_SUBJ = null;
    private String SQL_UPDATE_SUBJ = null;
    private String SQL_DELETE_SUBJ = null;
    private String SQL_GETALL_SUBJ = null;

    private PreparedStatement psCreateSubj = null;
    private PreparedStatement psReadSubj = null;
    private PreparedStatement psUpdSubj = null;
    private PreparedStatement psDelSubj = null;
    private PreparedStatement psGetAllSubj = null;

    // Constructor
    public MySqlSubjectDao(Connection connection, String selectDataBase) throws DaoException {
        try {
            Properties props = new Properties();
            InputStream stream = this.getClass().getResourceAsStream(selectDataBase);
            props.load(stream);

            this.SQL_CREATE_SUBJ = props.getProperty("SQL_CREATE_SUBJ");
            this.SQL_READ_SUBJ = props.getProperty("SQL_READ_SUBJ");
            this.SQL_UPDATE_SUBJ = props.getProperty("SQL_UPDATE_SUBJ");
            this.SQL_DELETE_SUBJ = props.getProperty("SQL_DELETE_SUBJ");
            this.SQL_GETALL_SUBJ = props.getProperty("SQL_GETALL_SUBJ");

            psCreateSubj = connection.prepareStatement(SQL_CREATE_SUBJ);
            psReadSubj = connection.prepareStatement(SQL_READ_SUBJ);
            psUpdSubj = connection.prepareStatement(SQL_UPDATE_SUBJ);
            psDelSubj = connection.prepareStatement(SQL_DELETE_SUBJ);
            psGetAllSubj = connection.prepareStatement (SQL_GETALL_SUBJ);
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    // Create a new DB entry as per corresponding received object
    @Override
    public void create(Subject subject) throws DaoException {
        try {
            psCreateSubj.setInt(1, subject.getId());
            psCreateSubj.setString(2, subject.getDescription());
            psCreateSubj.execute();
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
    }

    // Return the object corresponding to the DB entry with received primary 'key'
    @Override
    public Subject read(Subject subject) throws DaoException {
        ResultSet rsReadSubj = null;
        try {
            psReadSubj.setInt(1, subject.getId());
            rsReadSubj = psReadSubj.executeQuery();
            rsReadSubj.next();
            subject.setId(rsReadSubj.getInt("ID"));
            subject.setDescription(rsReadSubj.getString("DESCRIPTION"));
            return subject;
        } catch (SQLException exc) {
            throw new DaoException ("Exception for Dao", exc);
        }
        finally {
            try {
                if (rsReadSubj != null) {
                    rsReadSubj.close();
                } else {
                    System.err.println ("RS set of table results was not created");
                }
            } catch (SQLException exc) {
                throw new DaoException ("Exception for DAO", exc);
            }
        }
    }

    // Modify the DB entry as per corresponding received object
    @Override
    public void update(Subject subject) throws DaoException {
        try {
            psUpdSubj.setString(1, subject.getDescription());
            psUpdSubj.setInt(2, subject.getId());
            psUpdSubj.execute();
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
    }

    // Remove the DB entry as per corresponding received object
    @Override
    public void delete(Subject subject) throws DaoException {
        try {
            psDelSubj.setInt(1, subject.getId());
            psDelSubj.execute();
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
    }

    // Return a list of objects corresponding to all DB entries
    @Override
    public List<Subject> getAll() throws DaoException {
        ResultSet rsGetAllSubj = null;
        try {
            rsGetAllSubj = psGetAllSubj.executeQuery();
            List<Subject> list = new ArrayList<Subject>();
            while (rsGetAllSubj.next()) {
                Subject tempSubj1 = new Subject(0, null);
                tempSubj1.setId(rsGetAllSubj.getInt("ID"));
                tempSubj1.setDescription(rsGetAllSubj.getString("DESCRIPTION"));
                list.add(tempSubj1);
            }
            return list;
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
        finally {
            try {
                if (rsGetAllSubj != null) {
                    rsGetAllSubj.close();
                } else {
                    System.err.println ("RS set of table results was not created");
                }
            } catch (SQLException exc) {
                throw new DaoException ("Exception for DAO", exc);
            }
        }
    }

    // Terminate 'PreparedStatement' object received as an argument
    private void closePs(PreparedStatement dummyPs) throws DaoException {
        if (dummyPs != null) {
            try {
                dummyPs.close();
                //throw new SQLException(); // Uncomment this line to test exception handling
            } catch (SQLException exc) {
                throw new DaoException("Exception for Dao", exc);
            }
        } else {
            System.err.println ("PS statement was not created");
        }
    }

    // Terminate all 'PreparedStatement' objects
    public void close() throws DaoException {
        DaoException exc = null;
        try {
            try {
                closePs(psCreateSubj);
            } catch (DaoException e) {
                exc = e;
            }
            try {
                closePs(psReadSubj);
            } catch (DaoException e) {
                exc = e;
            }
            try {
                closePs(psUpdSubj);
            } catch (DaoException e) {
                exc = e;
            }
            try {
                closePs(psDelSubj);
            } catch (DaoException e) {
                exc = e;
            }
        } finally {
            if (exc != null) {
                throw exc;
            }
        }
    }
}
