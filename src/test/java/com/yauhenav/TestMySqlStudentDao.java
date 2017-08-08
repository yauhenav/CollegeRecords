package com.yauhenav;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
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

    private static Connection connection;
    private static String pathDB = "/testDataBase.properties";
    private static MySqlStudentDao testMSSD;
    private static PreparedStatement psEmpty = null;
    private static PreparedStatement psPopulate = null;

    @BeforeClass
    public static void createPsAndConnection() throws SQLException, DaoException {
        try {
            MySqlDaoFactory testMSDF = new MySqlDaoFactory(pathDB);
            connection = testMSDF.connection;
            testMSSD = new MySqlStudentDao(connection, pathDB);
            psPopulate = connection.prepareStatement("INSERT INTO `STUDENT` (`ID`,`NAME`,`SURNAME`)"+
                    "VALUES (1,'BILL','CLINTON');" +
                    "INSERT INTO `SUBJECT` (`ID`,`DESCRIPTION`) VALUES (1,'MATHEMATICS');" +
                    "INSERT INTO `SUBJECT` (`ID`,`DESCRIPTION`) VALUES (2,'LITERATURE');" +
                    "INSERT INTO `SUBJECT` (`ID`,`DESCRIPTION`) VALUES (3,'STATISTICS');" +
                    "INSERT INTO `SUBJECT` (`ID`,`DESCRIPTION`) VALUES (4,'ECONOMICS');" +
                    "INSERT INTO `SUBJECT` (`ID`,`DESCRIPTION`) VALUES (5,'ENGLISH');" +
                    "INSERT INTO `SUBJECT` (`ID`,`DESCRIPTION`) VALUES (6,'INFORMATION TECHNOLOGY');" +
                    "INSERT INTO `MARK` (`ID`,`VALUE`,`STUDENT_ID`,`SUBJECT_ID`) VALUES (1,10,1,1);" +
                    "INSERT INTO `MARK` (`ID`,`VALUE`,`STUDENT_ID`,`SUBJECT_ID`) VALUES (2,9,1,2);" +
                    "INSERT INTO `MARK` (`ID`,`VALUE`,`STUDENT_ID`,`SUBJECT_ID`) VALUES (3,8,1,3);" +
                    "INSERT INTO `MARK` (`ID`,`VALUE`,`STUDENT_ID`,`SUBJECT_ID`) VALUES (4,7,1,4);" +
                    "INSERT INTO `MARK` (`ID`,`VALUE`,`STUDENT_ID`,`SUBJECT_ID`) VALUES (5,6,1,5);" +
                    "INSERT INTO `MARK` (`ID`,`VALUE`,`STUDENT_ID`,`SUBJECT_ID`) VALUES (6,5,1,6);");
            psEmpty = connection.prepareStatement("DELETE FROM `testDataBase`.`STUDENT`;" +
                    "DELETE FROM `testDataBase`.`SUBJECT`; DELETE FROM `testDataBase`.`MARK`;");
        } catch (SQLException | DaoException exc) {
            exc.printStackTrace();
        }
    }

    @Before
    public void populateDataBase() throws SQLException {
        try {
            psPopulate.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @After
    public void emptyDataBase() throws SQLException {
        try {

            psEmpty.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @AfterClass
    public static void closePSandConnection() throws SQLException {
        try {
            psEmpty.close();
            psPopulate.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void TestCreateStudentMethod () throws SQLException, DaoException {
        try {
            Student testStudentPush = new Student (12, "Sherlock", "Holmes" );
            testMSSD.create(testStudentPush);

            PreparedStatement ps = connection.prepareStatement("SELECT ID, NAME, SURNAME FROM testDataBase.STUDENT WHERE ID = ?");
            ps.setInt(1, testStudentPush.getId());
            ResultSet rs = ps.executeQuery();
            Student testStudentPop = new Student(0, null, null);
            rs.next();
            testStudentPop.setId(rs.getInt("ID"));
            testStudentPop.setName(rs.getString("NAME"));
            testStudentPop.setSurname(rs.getString("SURNAME"));

            assertEquals (testStudentPush.toString(), testStudentPop.toString());
        } catch (DaoException exc) {
            exc.printStackTrace();
        }
    }
}
