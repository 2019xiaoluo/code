package cn.edu.jsu.lzj.sms.dao;

import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.Course;
import cn.edu.jsu.lzj.sms.vo.Student;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 对course表的相关操作
 */
public class CourseDAO {
    /**
     * 获取数据库连接的常量
     */
    public static Connection conn=new DatabaseConnection().getConnection();

    /**
     * 向数据库course表中插入课程
     * @param vo
     * @return {@code boolean}
     */
    public static boolean doInsert(Course vo){
        //插入课程往数据库中
        String sql="replace into course values(?,?,?,?)";
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            prst.setString(1,vo.getCno());
            prst.setString(2,vo.getCname());
            prst.setString(3,vo.getcTeacher());
            prst.setDouble(4,vo.getCredit());
            prst.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取表中所有课程信息
     * @return
     */
    public static ArrayList<Course> getAllcount(){
        //返回course表中的所有课程
       ArrayList<Course> alist=new ArrayList<>();
        String sql="select * from course";
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                String cno=resultSet.getString(1);
                String cname=resultSet.getString(2);
                String cTeacher=resultSet.getString(3);
                Double credit=resultSet.getDouble(4);
                Course vo=new Course(cno,cname,cTeacher,credit);
                alist.add(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return alist;
    }

    /**
     * 获取表中所有课程名
     * @return
     */
    public static ArrayList<String> getAllCno(){
        //返回course表中的所有课程号
        ArrayList<String> alist=new ArrayList<>();
        String sql="select cno from course";
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                alist.add(resultSet.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * 根据Course对course进行更新
     * @param vo
     * @return
     */
    public static int doUpdate(Course vo){
        //数据库更新操作
        String cno =vo.getCno();
        String sql ="update course set cname='"+vo.getCname()+"',cTeacher='"+vo.getcTeacher()+
                "',credit='"+vo.getCredit()+"' where cno='"+cno+"'";
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            return prst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除Course对象
     * @param vo
     * @return
     */
    public static int doDelete(Course vo){
        String cno =vo.getCno();
        String sql ="delete from course where cno='"+cno+"'";
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            return prst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回表中部分数据，为分页做准备
     * @param start
     * @param end
     * @return
     */
    public static ArrayList<Course> doPage(int start,int end){
        //返回course表中的部分课程，进行分页
        ArrayList<Course> alist=new ArrayList<>();
        String sql="select * from course limit "+start+","+end;
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                String cno=resultSet.getString(1);
                String cname=resultSet.getString(2);
                String cTeacher=resultSet.getString(3);
                Double credit=resultSet.getDouble(4);
                Course vo=new Course(cno,cname,cTeacher,credit);
                alist.add(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * 根据课程号返回课程名
     * @param cno
     * @return
     */
    public static String findNameByCno(String cno){
        String cname=null;
        String sql="select cname from course where cno="+cno;
        try(Statement stmt = conn.createStatement()){
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next())
                cname=resultSet.getString(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cname;
    }

    /**
     * 这是个根据课程号返回课程对象的方法
     */
    public static Course findByCno(String cno){
        Course course=null;
        String sql="select * from course where cno="+cno;
        try(Statement stmt = conn.createStatement()){
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next())
            {
                course=new Course(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return course;
    }


}
