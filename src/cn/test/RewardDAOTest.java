package cn.test;

import cn.edu.jsu.lzj.sms.dao.RewardDAO;
import org.junit.Test;

import static org.junit.Assert.*;

public class RewardDAOTest {


    @Test
    public void findRewardBySno() {
        assertNotNull(RewardDAO.findRewardBySno(2019));
    }
    @Test
    public void doInsertCourse(){
        assertTrue(RewardDAO.doInsertCourse("1000",2019401999));
        ;
    }
}