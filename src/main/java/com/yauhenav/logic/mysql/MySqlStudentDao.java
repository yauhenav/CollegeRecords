package com.yauhenav.logic.mysql;

import java.sql.*;
import java.util.*;

import com.yauhenav.logic.dao.*;
import com.yauhenav.logic.dto.*;
import com.yauhenav.logic.exception.*;

public class MySqlStudentDao implements StudentDao {
    private final static String SQL_CREATE = "INSERT INTO daotrain.STUDENT (ID, NAME, SURNAME) VALUES (?, ?, ?)";
    private final static String SQL_READ = "SELECT ID, NAME, SURNAME FROM daotrain.STUDENT WHERE ID = ?";
    private final static String SQL_UPDATE = "UPDATE daotrain.STUDENT SET NAME = ?, SURNAME = ? WHERE ID = ?";
    private final static String SQL_DELETE = "DELETE FROM daotrain.STUDENT WHERE ID = ?";
    private final static String SQL_GETALL = "SELECT ID, NAME, SURNAME FROM daotrain.STUDENT";

    private PreparedStatement psCreateStud = null;
    private PreparedStatement psReadStud = null;
    private PreparedStatement psUpdStud = null;
    private PreparedStatement psDelStud = null;
    private PreparedStatement psGetAllStud = null;

    // Constructor
    public MySqlStudentDao(Connection connection) throws DaoException {
        try {
            psCreateStud = connection.prepareStatement(SQL_CREATE);
            psReadStud = connection.prepareStatement(SQL_READ);
            psUpdStud = connection.prepareStatement(SQL_UPDATE);
            psDelStud = connection.prepareStatement(SQL_DELETE);
            psGetAllStud = connection.prepareStatement(SQL_GETALL);
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO");
        }
    }

    // Create a new DB entry as per corresponding received object
    @Override
    public void create(Student student) throws DaoException {
        try {
            psCreateStud.setInt(1, student.getId());
            psCreateStud.setString(2, student.getName());
            psCreateStud.setString(3, student.getSurname());
            psCreateStud.execute();
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO");
        }
    }

    // Return the object corresponding to the DB entry with received primary 'key'
    @Override
    public Student read(Student student) throws DaoException {
        ResultSet rsReadStud = null;
        try {
            psReadStud.setInt(1, student.getId());
            rsReadStud = psReadStud.executeQuery();
            rsReadStud.next();
            student.setId(rsReadStud.getInt("ID"));
            student.setName(rsReadStud.getString("NAME"));
            student.setSurname(rsReadStud.getString("SURNAME"));
            return student;
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO");
        }
        finally {
            try {
                if (rsReadStud != null) {
                    rsReadStud.close();
                } else {
                    System.err.println ("RS set of table results was not created");
                }
            } catch (SQLException exc) {
                throw new DaoException ("Exception for DAO");
            }
        }
    }

    // Modify the DB entry as per corresponding received object
    @Override
    public void update(Student student) throws DaoException {
        try {
            psUpdStud.setString(1, student.getName());
            psUpdStud.setString(2, student.getSurname());
            psUpdStud.setInt(3, student.getId());
            psUpdStud.execute();
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
    }

    // Remove the DB entry as per corresponding received object
    @Override
    public void delete(Student student) throws DaoException {
        try {
            psDelStud.setInt (1, student.getId());
            psDelStud.execute();
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
    }

    // Return a list of objects corresponding to all DB entries
    @Override
    public List<Student> getAll() throws DaoException {
        ResultSet rsGetAllStud = null;
        try {
            rsGetAllStud = psGetAllStud.executeQuery();
            List<Student> lst = new ArrayList<Student>();
            while (rsGetAllStud.next()) {
                Student stud = new Student(0, null, null);
                stud.setId(rsGetAllStud.getInt("ID"));
                stud.setName(rsGetAllStud.getString("NAME"));
                stud.setSurname(rsGetAllStud.getString("SURNAME"));
                lst.add(stud);
            }
            return lst;
        } catch (SQLException exc) {
            throw new DaoException ("Exception for DAO", exc);
        }
        finally {
            try {
                if (rsGetAllStud != null) {
                    rsGetAllStud.close();
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
    @Override
    public void close() throws DaoException {
        DaoException exc = null;
        try {
            try {
                this.closePs(psCreateStud);
            } catch (DaoException e) {
                exc = e;
            }
            try {
                this.closePs(psReadStud);
            } catch (DaoException e) {
                exc = e;
            }
            try {
                this.closePs(psUpdStud);
            } catch (DaoException e) {
                System.out.println("3rd exc thrown");
                exc = e;
            }
            try {
                this.closePs(psDelStud);
            } catch (DaoException e) {
                exc = e;
            }
            try {
                this.closePs(psGetAllStud);
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

