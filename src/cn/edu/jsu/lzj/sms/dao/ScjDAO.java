package cn.edu.jsu.lzj.sms.dao;

import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;

import cn.edu.jsu.lzj.sms.util.OperateDatabase;
import cn.edu.jsu.lzj.sms.vo.Scj;
import cn.edu.jsu.lzj.sms.vo.Student;
import com.sun.jdi.IntegerType;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * scj表的相关操作
 */

public class ScjDAO {
    /**
     * 数据库连接常量
     */
    public static Connection conn=new DatabaseConnection().getConnection();

    /**
     * 将Scj对象插入scj表中
     * @param vo
     * @return
     */
    public static boolean doInsert(Scj vo){
        //插入成绩往数据库中
        String sql="replace into scj values(?,?,?)";
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            prst.setString(1,vo.getCno());
            prst.setInt(2,vo.getSno());
            prst.setDouble(3,vo.getGrade());
            prst.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 初始化scj表格
     */
    public static void doinitScj(){
        //初始化所有学生必修课成绩，默认cno0开头为必修
        ArrayList<String> allCno = CourseDAO.getAllCno();
        ArrayList<Integer> allSno = StudentDAO.getAllSno();
        for (String s : allCno) {
            if (s.startsWith("0")){
                //必修课,每位学生都有成绩
                for (Integer i : allSno) {
                    Scj vo=new Scj(s,i, OperateDatabase.getNum(0,100)*1.0);
                    doInsert(vo);
                }
            }else{
                //选修课
                for (int i=0;i<300;i++){
                    //每门选修课随机生成200个学生的成绩
                    Scj vo=new Scj(s,allSno.get(OperateDatabase.getNum(0,allSno.size())), OperateDatabase.getNum(0,100)*1.0);
                    doInsert(vo);
                }
            }
        }
        System.out.println("初始化成功");
    }

    /**
     * 获取所有scj表格的所有值
     * @return
     */
    public static ArrayList<Scj> getAllcount(){
        //获取Scj中的所有行
        String sql="select * from scj";
        return doSql(sql);
    }

    /**
     * 为分页做准备
     * @param start
     * @param end
     * @return
     */
    public static ArrayList<Scj> doPage(int start,int end){
        //获取Scj中的部分行，为分页做准备
        String sql="select * from scj limit "+start+","+end;
        return doSql(sql);
    }

    /**
     * 为按课程号分页
     * @param start
     * @param end
     * @param cno
     * @return
     */
    public static ArrayList<Scj> doPageByCno(int start,int end,String cno){
        //获取Scj中的部分行，为分页做准备
        String sql="select * from scj where cno=" +cno+" limit "+start+","+end;
        return doSql(sql);
    }

    /**
     * 通过cno获取scj对象
     * @param c
     * @return
     */
    public static ArrayList<Scj> getAllbyCno(String c){
        //获取Scj中的部分行，为按课程号查找做准备
        String sql="select * from scj where cno='"+c+"'";
        return doSql(sql);
    }

    /**
     * 通过学号获取scj对象
     * @param c
     * @return
     */
    public static ArrayList<Scj> getAllbySno(String c){
        //获取Scj中的部分行，为按学号查找准备
        String sql="select * from scj where sno="+c;
        return doSql(sql);
    }

    /**
     * 为按学号分页做准备
     * @param start
     * @param end
     * @param sno
     * @return
     */
    public static ArrayList<Scj> doPagebySno(int start,int end,Integer sno){
        String sql="select * from scj where cno=" +sno+" limit "+start+","+end;
        return doSql(sql);
    }

    /**
     * 按成绩课程号分页
     * @param start 分页的开始
     * @param end 分页一共有几条
     * @param start0 成绩开始区间
     * @param end0 成绩结束
     * @param cno 课程号
     * @return
     */
    public static ArrayList<Scj> doPagebyGrade(int start,int end,int start0,int end0,String cno){
        //返回区间的小集合
        String sql="select * from scj  where grade between "+start0+" and "+end0+" and cno="+cno+" limit "+start+","+end;
        return doSql(sql);
    }

    /**
     * 获取所有的成绩区间类的部分scj对象
     * @param start0
     * @param end0
     * @param cno
     * @return
     */
    public static ArrayList<Scj> getAllbyGrade(int start0,int end0,String cno){
        //返回区间的大集合
        String sql="select * from scj  where grade between "+start0+" and "+end0+" and cno="+cno;
        return doSql(sql);
    }

    /**
     * 执行sql语句，减少重复
     * @param sql
     * @return
     */
    public static ArrayList<Scj> doSql(String sql){
        //执行sql语句，减少重复
        ArrayList<Scj> alist=new ArrayList<>();
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                //遍历结果，把每一行的数据取出构成一个Scj对象
                String cno=resultSet.getString(1);
                int sno=resultSet.getInt(2);
                Double grade=resultSet.getDouble(3);
                Scj s=new Scj(cno,sno,grade);
                alist.add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * 获取课程的最大成绩
     * @param cno
     * @return
     */
    public static ArrayList<Scj> getMaxGradeByCno(String cno){
        String sql="select * from scj where grade=(select distinct max(grade) from scj where cno="+cno+")"+" and cno="+cno;
        return doSql(sql);
    }

    /**
     * 获取课程的最小成绩
     * @param cno
     * @return
     */
    public static ArrayList<Scj> getMinGradeByCno(String cno){
        String sql="select * from scj where grade=(select distinct min(grade) from scj where cno="+cno+")"+" and cno="+cno;
        return doSql(sql);
    }

    /**
     * 获取平均成绩
     * @param cno
     * @return
     */
    public static ArrayList<Double> getAvgGradeByCno(String cno){
        String sql="select avg(grade) from scj where cno="+cno;
        ArrayList<Double> alist=new ArrayList<>();
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                //遍历结果，把每一行的数据取出构成一个Scj对象
               Double d=resultSet.getDouble(1);
                alist.add(d);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * 获取一共多少人学习该课程
     * @param cno
     * @return
     */
    public static ArrayList<Integer> getTotalGradeByCno(String cno){
        //返回课程多少人学习
        String sql="select count(sno) from scj where cno="+cno;
        ArrayList<Integer> alist=new ArrayList<>();
        try(PreparedStatement prst = conn.prepareStatement(sql);){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()){
                Integer d=resultSet.getInt(1);
                alist.add(d);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alist;
    }

    /**
     * 这是个通过课程号和学号来找成绩的方法
     * @param sno,cno
     */
    public static Double findGradeBySnoCno(Integer sno,String cno){
        String sql="select * from scj where sno="+sno+" and cno="+cno;
        ArrayList<Scj> scjs = doSql(sql);
        if (scjs.size()>0)
            return scjs.get(0).getGrade();
        else
            return -0.0;
    }
}
