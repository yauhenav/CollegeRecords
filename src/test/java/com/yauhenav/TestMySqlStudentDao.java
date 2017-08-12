package com.yauhenav;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yauhenav.logic.exception.DaoException;
import com.yauhenav.logic.mysql.*;
import com.yauhenav.logic.dto.*;
import java.sql.*;

/**
 * Created by yauhenav on 16.7.17.
 */
public class TestMySqlStudentDao {

   private TestsHandler testHandler;
   private MySqlStudentDao testMSSD;

   public TestMySqlStudentDao() throws SQLException, DaoException {
       testHandler = new TestsHandler();
   }

    @Before
    public void fillDBWithTestData() throws SQLException, DaoException {
        try {
            testHandler.populateDataBase();
            testMSSD = testHandler.getMySqlStudentDao();
        } catch (SQLException | DaoException exc) {
            exc.printStackTrace();
        }
    }

    @After
    public void wipeDBTestData() throws SQLException, DaoException {
        try {
            testHandler.emptyDataBase();
            testHandler.close();
        } catch (DaoException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void TestCreateStudentMethod () throws SQLException, DaoException {
        try {
            Student testStudentPush = new Student (12, "Sherlock", "Holmes" );
            testMSSD.create(testStudentPush);
            Student testStudentPop = testMSSD.read(testStudentPush);
            assertEquals (testStudentPush.toString(), testStudentPop.toString());
        } catch (DaoException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void TestReadStudentMethod () throws SQLException, DaoException {
       try {

       }
    }
}
