package cn.edu.jsu.lzj.sms.vo;

import java.io.Serializable;
import java.sql.Date;

/**
 * 对应数据库中的student表
 * @author 罗自觐
 */

public class Student implements Serializable {
    private Integer sno;//学号
    private String sname;//姓名
    private String ssex;//性别
    private Integer sage;//年龄
    private String sxy;//所在学院
    private Date sschooltime;//入学时间年月日
    private String sclass;//学生班级

    public Student() {
    }

    public Student(Integer sno, String sname, String ssex, Integer sage, String sxy, Date sschooltime, String sclass) {
        this.sno = sno;
        this.sname = sname;
        this.ssex = ssex;
        this.sage = sage;
        this.sxy = sxy;
        this.sschooltime = sschooltime;
        this.sclass = sclass;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getSxy() {
        return sxy;
    }

    public void setSxy(String sxy) {
        this.sxy = sxy;
    }

    public Integer getSage() {
        return sage;
    }

    public void setSage(Integer sage) {
        this.sage = sage;
    }

    public Date getSschooltime() {
        return sschooltime;
    }

    public void setSschooltime(Date sschooltime) {
        this.sschooltime = sschooltime;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sno=" + sno +
                ", sname='" + sname + '\'' +
                ", ssex=" + ssex +
                ", sage=" + sage +
                ", sxy=" + sxy +
                ", sschooltime=" + sschooltime +
                '}';
    }
}
