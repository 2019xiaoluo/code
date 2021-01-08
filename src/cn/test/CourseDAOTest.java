package cn.test;

import cn.edu.jsu.lzj.sms.dao.CourseDAO;
import cn.edu.jsu.lzj.sms.vo.Course;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseDAOTest {

    @Test
    public void findByCno() {
        assertNotNull(CourseDAO.findByCno("0001"));
    }

    @Test
    public void doInsert() {
        assertTrue(CourseDAO.doInsert(new Course("0007","大学物理","周康",3.0)));
    }

    @Test
    public void getAllcount() {
        assertNotNull(CourseDAO.getAllcount());
    }

    @Test
    public void getAllCno() {
        assertNotNull(CourseDAO.getAllCno());
    }

    @Test
    public void doUpdate() {
        assertEquals(1,CourseDAO.doUpdate(new Course("0007","大学物理","王益斌",3.0)));
    }


    @Test
    public void doPage() {
        assertNotNull(CourseDAO.doPage(0,9));
    }

    @Test
    public void findNameByCno() {
        assertNotNull(CourseDAO.findNameByCno("0001"));
    }

    @Test
    public void doDelete() {
        assertEquals(1,CourseDAO.doDelete(new Course("0007","大学物理","王益斌",3.0)));
    }

}