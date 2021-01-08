package cn.test;

import cn.edu.jsu.lzj.sms.dao.ScjDAO;
import cn.edu.jsu.lzj.sms.vo.Scj;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScjDAOTest {

    @Test
    public void doInsert() {
        assertTrue(ScjDAO.doInsert(new Scj("0001",0,99.0)));;
    }

    @Test
    public void getAllcount() {
        assertNotNull(ScjDAO.getAllcount());
    }

    @Test
    public void doPage() {
        assertNotNull(ScjDAO.doPage(1,5));
    }

    @Test
    public void doPageByCno() {
        assertNotNull(ScjDAO.doPageByCno(1,5,"0001"));
    }

    @Test
    public void getAllbyCno() {
        assertNotNull(ScjDAO.getAllbyCno("0001"));
    }

    @Test
    public void getAllbySno() {
        assertNotNull(ScjDAO.getAllbySno("0"));
    }

    @Test
    public void doPagebySno() {
        assertNotNull(ScjDAO.doPagebySno(1,5,0));
    }

    @Test
    public void doPagebyGrade() {
        assertNotNull(ScjDAO.doPagebyGrade(1,5,20,30,"0001"));
    }

    @Test
    public void getAllbyGrade() {
        assertNotNull(ScjDAO.getAllbyGrade(20,30,"0001"));
    }


    @Test
    public void getMaxGradeByCno() {
        assertNotNull(ScjDAO.getMaxGradeByCno("0001"));
    }

    @Test
    public void getMinGradeByCno() {
        assertNotNull(ScjDAO.getMinGradeByCno("0001"));
    }

    @Test
    public void getAvgGradeByCno() {
        assertNotNull(ScjDAO.getAvgGradeByCno("0001"));
    }

    @Test
    public void getTotalGradeByCno() {
        assertNotNull(ScjDAO.getTotalGradeByCno("0001"));
    }

    @Test
    public void findGradeBySnoCno() {
        assertNotNull(ScjDAO.findGradeBySnoCno(0,"0001"));
    }
}