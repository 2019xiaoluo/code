package cn.edu.jsu.lzj.sms.dao;

import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.Reward;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 这是一个reward表相对操作的类
 * @author 罗自觐
 */
public class RewardDAO {
    /**
     * 这是一个连接数据库的静态变量
     */
    public static Connection conn=new DatabaseConnection().getConnection();
    /**
     * 这是一个通过学号查询学生的奖惩情况和选课情况的方法
     * @param sno
     * @return {@code Reward} 返回一个Reward对象
     */
    public static Reward findRewardBySno(Integer sno){
        Reward r=null;
        String sql="select * from reward where sno="+sno;
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet resultSet = prst.executeQuery();
            if (resultSet.next()){
               int ssno = resultSet.getInt(1);
               String rewardmessage = resultSet.getString(2);
               String altercourse = resultSet.getString(3);
               Double gerencrdit = resultSet.getDouble(4);
                r=new Reward(ssno,rewardmessage,altercourse,gerencrdit);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return r;
    }

    /**
     * 这是个插入课程号的方法
     * @param cno
     * @return
     */
    public static boolean doInsertCourse(String cno,Integer sno){
        cno="#"+cno;
        String sql="update reward set altercourse=concat(altercourse,'"+cno+"') where sno="+sno;
        try(final PreparedStatement prst = conn.prepareStatement(sql)){
            if (prst.executeUpdate()>0)
                return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
