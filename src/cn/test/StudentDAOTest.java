package cn.test;

import cn.edu.jsu.lzj.sms.dao.StudentDAO;
import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class StudentDAOTest {
    Connection conn;
    @Before
    public void setUp(){
        conn=new DatabaseConnection().getConnection();
    }

    @Test
    public void findStudentBySno() {
        Integer id=2019401999;
        assertNotNull(StudentDAO.findStudentBySno(id));
    }

    @After
    public void tearUp(){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void doInsert() {
        assertTrue(StudentDAO.doInsert(new Student(3,"Liu","n",19,"ssss", Date.valueOf("2019-09-01"),"2019级1班")));
    }

    @Test
    public void getAllaccount() {
        assertNotNull(StudentDAO.getAllaccount());
    }

    @Test
    public void getAllSno() {
        assertNotNull(StudentDAO.getAllSno());
    }


    @Test
    public void doUpdate() {
        assertEquals(1,StudentDAO.doUpdate(new Student(3,"Liu","n",40,"ssss", Date.valueOf("2019-09-01"),"2019级1班")));
    }

    @Test
    public void doPage() {
        assertNotNull(StudentDAO.doPage(1,5));
    }

    @Test
    public void findNameBySno() {
        assertNotNull(StudentDAO.findNameBySno(0));
    }

    @Test
    public void doDelete() {
        assertNotNull(StudentDAO.doDelete(new Student(3,"Liu","nan",40,"ssss", Date.valueOf("2019-09-01"),"2019级1班")));
    }
}