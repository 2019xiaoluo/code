package cn.edu.jsu.lzj.sms.dao;

import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.AccountNum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 操作accountnum表的
 */
public class AccountNumDAO {
    /**
     * 这是一个连接数据库的常量
     */
    public static Connection conn=new DatabaseConnection().getConnection();

    /**
     * 检查id是否在数据库中
     * @param vo
     * @return
     */
    public static boolean checkid(AccountNum vo){
        //判断账号密码是否正确
        String id=vo.getId().toString();
        String sql="select * from accountnum where id="+id;//从账号表中查询密码是否为管理员
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet resultSet = prst.executeQuery();
            if (!resultSet.next()){
                return false;
            }else{
                int aid = resultSet.getInt(1);
                String apassword = resultSet.getString(2);
                int aisTeacher=resultSet.getInt(3);
                if (aid==vo.getId() && apassword.equals(vo.getPassword()) && aisTeacher==vo.getIsTeacher()){
                    return true;
                }
                else
                    return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将AccountNum插入数据库中
     * @param vo
     * @return
     */
    public static boolean doInsert(AccountNum vo){
        String sql="replace into accountnum values(?,?,?)";
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            prst.setInt(1,vo.getId());
            prst.setString(2,vo.getPassword());
            prst.setInt(3,vo.getIsTeacher());
            prst.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 初始化accountnum表
     */
    static void doinitaccountnum(){
        //初始化账号表
        ArrayList<Integer> allSno = StudentDAO.getAllSno();
        for (Integer i : allSno) {
            AccountNum a=new AccountNum(i,"123456",0);
            //设置初始密码为123456，学生账户
            doInsert(a);
        }

    }

    /**
     * 这是一个修改密码的方法，根据新传进来的AccountNum更改
     * @param vo
     * @return {@code boolean}返回是否修改成功
     */
    public static boolean doUpdatePassword(AccountNum vo){
        String id=vo.getId().toString();
        String sql="UPDATE accountnum set password='"+vo.getPassword()+"' where id="+id;//从账号表中查询密码是否为管理员
        try(PreparedStatement prst = conn.prepareStatement(sql)){
           if (prst.executeUpdate()>0){
               return true;
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
