package com.yauhenav;

import static org.junit.Assert.*;

import com.yauhenav.logic.exception.DaoException;
import com.yauhenav.logic.mysql.MySqlDaoFactory;
import com.yauhenav.logic.mysql.MySqlStudentDao;
import com.yauhenav.logic.dao.*;
import org.junit.Before;
import org.junit.Test;
import com.yauhenav.logic.dto.*;

import java.sql.ResultSet;
import java.sql.*;

/**
 * Created by yauhenav on 16.7.17.
 */
public class TestMySqlStudentDao {

    @Before
    public void connectToDataBase() throws SQLException {

    }

    @Test
    public void TestCreateStudentMethod () throws SQLException, DaoException {
        try {
            MySqlDaoFactory testMSDF = new MySqlDaoFactory("/testDataBase.properties");
            StudentDao testInterStudentDao = testMSDF.getStudentDao();
            Student testStudentPush = new Student (11, "Sherlock", "Holmes" );
            testInterStudentDao.create(testStudentPush);

            Connection connection = testMSDF.connection;
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
