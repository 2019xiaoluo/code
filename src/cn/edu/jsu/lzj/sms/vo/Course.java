package cn.edu.jsu.lzj.sms.vo;

import java.io.Serializable;

/**
 * 对应course表
 * 默认cno--0开头为必修课1开头为选修课，cno只有4位数
 */

public class Course implements Serializable {
    private String cno;//课程号
    private String cname;//课程名
    private String cTeacher;//课程老师名
    private Double credit;//学分

    public Course() {
    }

    public Course(String cno, String cname, String cTeacher, Double credit) {
        this.cno = cno;
        this.cname = cname;
        this.cTeacher = cTeacher;
        this.credit = credit;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getcTeacher() {
        return cTeacher;
    }

    public void setcTeacher(String cTeacher) {
        this.cTeacher = cTeacher;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", cTeacher='" + cTeacher + '\'' +
                ", credit=" + credit +
                '}';
    }
}
