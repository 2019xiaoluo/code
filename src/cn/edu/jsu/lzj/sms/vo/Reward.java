package cn.edu.jsu.lzj.sms.vo;

import java.io.Serializable;

/**
 * 这是一个reward对应的实体类，用来记录获奖情况，选课情况，个人学分
 * @author 罗自觐
 */

public class Reward implements Serializable {
    private Integer sno;
    private String rewardMessage;//奖惩情况表示用#隔开
    private String alterCourse;//选课用学号表示用#隔开
    private Double gerenCredit;

    public Reward() {
    }

    public Reward(Integer sno, String rewardMessage, String alterCourse, Double gerenCredit) {
        this.sno = sno;
        this.rewardMessage = rewardMessage;
        this.alterCourse = alterCourse;
        this.gerenCredit = gerenCredit;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getRewardMessage() {
        return rewardMessage;
    }

    public void setRewardMessage(String rewardMessage) {
        this.rewardMessage = rewardMessage;
    }

    public String getAlterCourse() {
        return alterCourse;
    }

    public void setAlterCourse(String alterCourse) {
        this.alterCourse = alterCourse;
    }

    public Double getGerenCredit() {
        return gerenCredit;
    }

    public void setGerenCredit(Double gerenCredit) {
        this.gerenCredit = gerenCredit;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "sno=" + sno +
                ", rewardMessage='" + rewardMessage + '\'' +
                ", alterCourse='" + alterCourse + '\'' +
                ", gerenCredit=" + gerenCredit +
                '}';
    }
}
