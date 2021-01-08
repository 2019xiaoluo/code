package cn.edu.jsu.lzj.sms.vo;

import java.io.Serializable;

/**
 * 对应的accountnum表，用来存储相关账号的
 */
public class AccountNum implements Serializable{
    private Integer id;//账号
    private String password;//密码
    private Integer isTeacher;//账号是否为管理员，学生为0，管理员为1

    public AccountNum() {
    }

    public AccountNum(Integer id, String password, Integer isTeacher) {
        this.id = id;
        this.password = password;
        this.isTeacher = isTeacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Integer isTeacher) {
        this.isTeacher = isTeacher;
    }
}
