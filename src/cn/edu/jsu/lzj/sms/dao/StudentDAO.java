package cn.edu.jsu.lzj.sms.dao;


import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


/**
 * 操作student表的相关方法
 */
public class StudentDAO {
    /**
     * 这是一个连接数据库的静态成员
     */
    public static Connection conn=new DatabaseConnection().getConnection();

    /**
     * 这是一个将Student对象插入数据库的方法
     * @param vo
     * @return
     */
    public static boolean doInsert(Student vo){
        String sql="replace into student values(?,?,?,?,?,?,?)";
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            prst.setInt(1,vo.getSno());
            prst.setString(2,vo.getSname());
            prst.setString(3,vo.getSsex());
            prst.setInt(4,vo.getSage());
            prst.setString(5,vo.getSxy());
            prst.setDate(6,vo.getSschooltime());
            prst.setString(7,vo.getSclass());
            prst.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 这是一个获取所有学生对象集合的方法
     * @return
     */
    public static ArrayList<Student> getAllaccount(){
        //将数据库表中的数据取出来，返回一个学生集合
        ArrayList<Student> alist=new ArrayList<>();
        String sql="select * from student";
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                //遍历结果，把每一列得数据取出构成一个Student对象
                int sno=resultSet.getInt(1);
                String sname=resultSet.getString(2);
                String ssex=resultSet.getString(3);
                int sage=resultSet.getInt(4);
                String sxy=resultSet.getString(5);
                Date date=resultSet.getDate(6);
                String sclass=resultSet.getString(7);
                Student stu=new Student(sno,sname,ssex,sage,sxy,date,sclass);
                alist.add(stu);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return alist;
    }

    /**
     * 这是一个获取所有学号的方法
     * @return
     */
    public static ArrayList<Integer> getAllSno(){
        //将数据库表中的数据取出来，返回一个学生集合
        ArrayList<Integer> alist=new ArrayList<>();
        String sql="select sno from student";
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                //遍历结果，把每一列得数据取出构成一个Student对象
                int sno=resultSet.getInt(1);
                alist.add(sno);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return alist;
    }

    /**
     * 这是一个删除的方法
     * @param vo
     * @return
     */
    public static int doDelete(Student vo){
       String sno =vo.getSno().toString();
       String sql ="delete from student where sno="+sno;
       try(PreparedStatement prst = conn.prepareStatement(sql);){
           return prst.executeUpdate();
       }catch (Exception e){
           e.printStackTrace();
       }
       return 0;
    }

    /**
     * 这是一个更新操作，根据学号来修改
     * @param vo
     * @return
     */
    public static int doUpdate(Student vo){
        //数据库更新操作
        String sno =vo.getSno().toString();
        String sql ="update student set sname='"+vo.getSname()+"',ssex='"+vo.getSsex()+
                "',sage='"+vo.getSage()+"',sxy='"+vo.getSxy()+
                "',sschooltime='"+vo.getSschooltime()+
                "',sclass='"+vo.getSclass()+"' where sno="+sno;
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            return prst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 这是一个为分页做准备的方法，返回表中的部分行
     * @param start
     * @param count
     * @return
     */
    public static ArrayList<Student> doPage(int start, int count){
        //将数据库表中的数据取出来，返回一个学生集合,取部分数据，为分页做准备
        ArrayList<Student> alist=new ArrayList<Student>();
        String sql="select * from student limit "+start+","+count;
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                //遍历结果，把每一列得数据取出构成一个Student对象
                int sno=resultSet.getInt(1);
                String sname=resultSet.getString(2);
                String ssex=resultSet.getString(3);
                int sage=resultSet.getInt(4);
                String sxy=resultSet.getString(5);
                Date date=resultSet.getDate(6);
                String sclass=resultSet.getString(7);
                Student stu=new Student(sno,sname,ssex,sage,sxy,date,sclass);
                alist.add(stu);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return alist;
    }

    /**
     * 这是一个通过学号查找姓名的方法
     * @param sno
     * @return
     */
    public static String findNameBySno(Integer sno){
        String sname=null;
        String sql="select sname from student where sno="+sno.toString();
        try(Statement stmt = conn.createStatement()){
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next())
            sname=resultSet.getString(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sname;
    }

    /**
     * 这是一个通过学号查找一个Student对象
     * @param ssno
     * @return {@code Student}返回一个Student对象
     */
    public static Student findStudentBySno(Integer ssno){
        Student stu=null;
        String sql="select * from student where sno="+ssno;
        try(Statement stmt = conn.createStatement()){
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next())
            {
                int sno=resultSet.getInt(1);
                String sname=resultSet.getString(2);
                String ssex=resultSet.getString(3);
                int sage=resultSet.getInt(4);
                String sxy=resultSet.getString(5);
                Date date=resultSet.getDate(6);
                String sclass=resultSet.getString(7);
                stu=new Student(sno,sname,ssex,sage,sxy,date,sclass);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stu;
    }


}
