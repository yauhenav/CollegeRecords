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

    public Connection connection;
    String pathDB = "/testDataBase.properties";
    MySqlStudentDao testMSSD;

    @Before
    public void connectToDataBase() throws SQLException, DaoException {
        try {
            MySqlDaoFactory testMSDF = new MySqlDaoFactory(pathDB);
            connection = testMSDF.connection;
            testMSSD = new MySqlStudentDao(connection, pathDB);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO `STUDENT` (`ID`,`NAME`,`SURNAME`)"+
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
            ps.executeUpdate();

        } catch (DaoException exc) {
            exc.printStackTrace();
        }
    }

    @After
    public void closeConnection() throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `testDataBase`.`STUDENT`;" +
                    "DELETE FROM `testDataBase`.`SUBJECT`; DELETE FROM `testDataBase`.`MARK`;");
            ps.executeUpdate();

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
