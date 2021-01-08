package cn.edu.jsu.lzj.sms.vo;

import java.io.Serializable;

/**
 * 对应数据库中的scj表，学生的成绩课程表
 * @author 罗自觐
 */
public class Scj implements Serializable {
    private String cno;//课程号
    private Integer sno;//学生学号
    private Double grade;//对应课程成绩

    public Scj() {
    }

    public Scj(String cno, Integer sno, Double grade) {
        this.cno = cno;
        this.sno = sno;
        this.grade = grade;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Scj{" +
                "cno='" + cno + '\'' +
                ", sno=" + sno +
                ", grade=" + grade +
                '}';
    }
}
