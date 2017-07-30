package com.yauhenav;

import static org.junit.Assert.*;

import com.yauhenav.logic.exception.DaoException;
import com.yauhenav.logic.mysql.MySqlDaoFactory;
import com.yauhenav.logic.mysql.MySqlStudentDao;
import com.yauhenav.logic.dao.*;
import org.junit.Test;
import com.yauhenav.logic.dto.*;

/**
 * Created by yauhenav on 16.7.17.
 */
public class TestMySqlStudentDao {

    @Test
    public void TestCreateStudentMethod () throws DaoException {
        try {
            MySqlDaoFactory testMySqlDaoFactory = new MySqlDaoFactory("/testDataBase.properties");
            StudentDao testInterStudentDao = testMySqlDaoFactory.getStudentDao();
            Student testStudent = new Student (12, "Sherlock", "Holmes" );
            testInterStudentDao.create(testStudent);
            assertEquals ((testInterStudentDao.read(testStudent)), testStudent);

        } catch (DaoException exc) {
            exc.printStackTrace();
        }

    }
}
