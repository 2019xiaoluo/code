package cn.edu.jsu.lzj.sms.frm.manage;


import cn.edu.jsu.lzj.sms.dao.CourseDAO;
import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.Course;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * 增加课程的界面
 */
public class AddCourse extends JDialog {


    private JTextField textFieldXh;
    private JTextField textFieldXm;
    private JTextField textFieldAge;
    private JTextField textFieldSxy;
    private JTextField textFieldSsex;
    private JTextField textFieldTime;
    private JTextField textFieldSclass;
    private JTextField textFieldcno;
    private JTextField textFieldCname;
    private JTextField textFieldTeacher;
    private JTextField textFieldcredit;

    private static AddCourse instance=null;

    public static AddCourse getInstance(){
        if (instance==null){
            instance=new AddCourse();
        }
        return instance;
    }
    /**
     * Create the dialog.
     */
    private AddCourse() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("增加课程信息");
        setBounds(100, 100, 340, 265);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);


        JLabel lblCno = new JLabel("课程号：");
        lblCno.setBounds(26, 25, 58, 15);
        getContentPane().add(lblCno);

        textFieldcno = new JTextField();
        textFieldcno.setBounds(90, 22, 111, 21);
        getContentPane().add(textFieldcno);
        textFieldcno.setColumns(10);

        JLabel lblCnoh = new JLabel("");
        lblCnoh.setBounds(211, 25, 110, 15);
        getContentPane().add(lblCnoh);
        textFieldcno.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(checkCno(textFieldcno.getText())==1){
                    lblCnoh.setForeground(Color.red);
                    lblCnoh.setText("课程号重复");
                    lblCnoh.setVisible(true);
                }else if(checkCno(textFieldcno.getText())==2){
                    lblCnoh.setForeground(Color.red);
                    lblCnoh.setText("课程号为空");
                    lblCnoh.setVisible(true);
                }else if(checkCno(textFieldcno.getText())==3){
                    lblCnoh.setForeground(Color.red);
                    lblCnoh.setText("课程号只能为数字");
                    lblCnoh.setVisible(true);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                lblCnoh.setVisible(false);
            }
        });


        JLabel lblCname = new JLabel("课程名：");
        lblCname.setBounds(26, 63, 58, 15);
        getContentPane().add(lblCname);

        textFieldCname = new JTextField();
        textFieldCname.setBounds(90, 60, 111, 21);
        getContentPane().add(textFieldCname);
        textFieldCname.setColumns(10);

        JLabel lblTeacher = new JLabel("老师：");
        lblTeacher.setBounds(26, 104, 58, 15);
        getContentPane().add(lblTeacher);

        textFieldTeacher = new JTextField();
        textFieldTeacher.setBounds(90, 101, 111, 21);
        getContentPane().add(textFieldTeacher);
        textFieldTeacher.setColumns(10);

        JLabel lblcredit = new JLabel("学分：");
        lblcredit.setBounds(26, 138, 58, 15);
        getContentPane().add(lblcredit);

        textFieldcredit = new JTextField();
        textFieldcredit.setBounds(90, 135, 111, 21);
        getContentPane().add(textFieldcredit);
        textFieldcredit.setColumns(10);

        JButton btnAdd = new JButton("增加");
        btnAdd.addActionListener(e->{
            if (check()==1){
                String cno=textFieldcno.getText();
                String cname=textFieldCname.getText();
                String cTeacher=textFieldTeacher.getText();
                Double credit=Double.parseDouble(textFieldcredit.getText());
                Course vo=new Course(cno,cname,cTeacher,credit);
                boolean b = CourseDAO.doInsert(vo);
                if (b){
                    JOptionPane.showMessageDialog(null,"增加成功");
                }else {
                    JOptionPane.showMessageDialog(null,"数据格式有问题，请修改!");
                }


            }else{
                JOptionPane.showMessageDialog(null,"数据有问题请修改");
            }
        });
        btnAdd.setBounds(26, 190, 97, 23);
        getContentPane().add(btnAdd);


        JButton btnExit = new JButton("退出");
        btnExit.setBounds(151, 190, 97, 23);
        getContentPane().add(btnExit);
        btnExit.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"退出成功");
            AddCourse.super.dispose();
        });


    }

    /**
     * 检查课程号是否符合要求
     * @param name
     * @return
     */
    public int checkCno(String name){
        if (name.length()==0)
            return 2;//如果为0返回2；
        if (!name.matches("\\d+")){
            return 3;//只能为数字
        }

        String sql="select * from course where cno='"+name+"'";
        Connection conn = new DatabaseConnection().getConnection();
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet re = prst.executeQuery();
            if (re.next()){
                return 1;//课程号重复
            }else {
                return 0;//满足条件
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;

    }

    public int check(){
        int flag=0;
        if (checkCno(textFieldcno.getText())==0 && textFieldCname.getText().length()!=0
                && textFieldTeacher.getText().length()!=0 && textFieldcredit.getText().length()!=0
                ){
            flag=1;
        }

        return flag;
    }

}

