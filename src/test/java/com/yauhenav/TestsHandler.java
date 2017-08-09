package com.yauhenav;

import com.yauhenav.logic.exception.DaoException;
import com.yauhenav.logic.mysql.*;
import java.sql.*;
/**
 * Created by yauhenav on 8.8.17.
 */
public class TestsHandler {
    private Connection connection;
    private String pathDB = "/testDataBase.properties";
    private MySqlStudentDao testMSSD;
    private PreparedStatement psEmpty = null;
    private PreparedStatement psPopulate = null;

    public MySqlStudentDao getMySqlStudentDao() {
        return testMSSD;
    }

    public void getConnectionAndPS() throws SQLException, DaoException {
        try {
            MySqlDaoFactory testMSDF = new MySqlDaoFactory(pathDB);
            connection = testMSDF.getConnection();
            testMSSD = new MySqlStudentDao(connection, pathDB);
            psPopulate = connection.prepareStatement("INSERT INTO `STUDENT` (`ID`,`NAME`,`SURNAME`)" +
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

    public void populateDataBase() throws SQLException {
        try {
            psPopulate.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void emptyDataBase() throws SQLException {
        try {
            psEmpty.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void closePSandConnection() throws SQLException {
        try {
            psEmpty.close();
            psPopulate.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
