package cn.test;

import cn.edu.jsu.lzj.sms.dao.AccountNumDAO;
import cn.edu.jsu.lzj.sms.vo.AccountNum;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountNumDAOTest {

    @Test
    public void checkid() {
        assertTrue(AccountNumDAO.checkid(new AccountNum(2019401999,"123456",0)));
    }

    @Test
    public void doInsert() {
        assertTrue(AccountNumDAO.doInsert(new AccountNum(2,"123456",1)));
    }

    @Test
    public void doUpdatePassword() {
        assertTrue(AccountNumDAO.doUpdatePassword(new AccountNum(2019401999,"0",0)));
    }
}