package cn.edu.jsu.lzj.sms.frm.manage;

import cn.edu.jsu.lzj.sms.dao.StudentDAO;
import cn.edu.jsu.lzj.sms.dbc.DatabaseConnection;
import cn.edu.jsu.lzj.sms.vo.Student;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * 增加学生的界面
 */
public class AddStudent extends JDialog {


    private JTextField textFieldXh;
    private JTextField textFieldXm;
    private JTextField textFieldAge;
    private JTextField textFieldSxy;
    private JTextField textFieldSsex;
    private JTextField textFieldTime;
    private JTextField textFieldSclass;
    private static AddStudent instance=null;

    public static AddStudent getInstance(){
        if (instance==null){
            instance=new AddStudent();
        }
        return instance;
    }

    /**
     * Create the dialog.
     */
    private AddStudent() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("增加学生信息");
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblXh = new JLabel("学号：");
        lblXh.setBounds(38, 24, 58, 15);
        getContentPane().add(lblXh);

        JLabel lblXhh = new JLabel("");
        lblXhh.setBounds(239, 24, 174, 15);
        getContentPane().add(lblXhh);

        textFieldXh = new JTextField();
        textFieldXh.setBounds(123, 21, 80, 21);
        getContentPane().add(textFieldXh);
        textFieldXh.setColumns(10);

        //判断学号
        textFieldXh.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(checkXh(textFieldXh.getText())==1){
                    lblXhh.setForeground(Color.red);
                    lblXhh.setText("学号重复");
                    lblXhh.setVisible(true);
                }else if(checkXh(textFieldXh.getText())==2){
                    lblXhh.setForeground(Color.red);
                    lblXhh.setText("学号为空");
                    lblXhh.setVisible(true);
                }else if(checkXh(textFieldXh.getText())==3){
                    lblXhh.setForeground(Color.red);
                    lblXhh.setText("学号只能为数字");
                    lblXhh.setVisible(true);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                lblXhh.setVisible(false);
            }
        });

        JLabel lblXm = new JLabel("姓名：");
        lblXm.setBounds(38, 64, 58, 15);
        getContentPane().add(lblXm);

        textFieldXm = new JTextField();
        textFieldXm.setBounds(123, 61, 80, 21);
        getContentPane().add(textFieldXm);
        textFieldXm.setColumns(10);

        JLabel lblAge = new JLabel("年龄：");
        lblAge.setBounds(38, 106, 58, 15);
        getContentPane().add(lblAge);

        textFieldAge = new JTextField();
        textFieldAge.setBounds(123, 103, 80, 21);
        getContentPane().add(textFieldAge);
        textFieldAge.setColumns(10);

        JLabel lblSxy = new JLabel("学院：");
        lblSxy.setBounds(38, 145, 58, 15);
        getContentPane().add(lblSxy);

        textFieldSxy = new JTextField();
        textFieldSxy.setBounds(123, 142, 80, 21);
        getContentPane().add(textFieldSxy);
        textFieldSxy.setColumns(10);

        JLabel lblSsex = new JLabel("性别：");
        lblSsex.setBounds(239, 64, 58, 15);
        getContentPane().add(lblSsex);

        textFieldSsex = new JTextField();
        textFieldSsex.setBounds(330, 61, 86, 21);
        getContentPane().add(textFieldSsex);
        textFieldSsex.setColumns(10);

        JLabel lblSchooltime = new JLabel("入学时间：");
        lblSchooltime.setBounds(239, 106, 86, 15);
        getContentPane().add(lblSchooltime);

        textFieldTime = new JTextField();
        textFieldTime.setBounds(330, 103, 86, 21);
        getContentPane().add(textFieldTime);
        textFieldTime.setColumns(10);

        JLabel lblSclass = new JLabel("班级：");
        lblSclass.setBounds(239, 145, 58, 15);
        getContentPane().add(lblSclass);

        textFieldSclass = new JTextField();
        textFieldSclass.setBounds(330, 142, 86, 21);
        getContentPane().add(textFieldSclass);
        textFieldSclass.setColumns(10);

        JButton btnAdd = new JButton("增加");
        btnAdd.setBounds(100, 199, 97, 23);
        getContentPane().add(btnAdd);
        btnAdd.addActionListener(e->{
            if (check()==1){
                int sno=Integer.parseInt(textFieldXh.getText());
                String sname=textFieldXm.getText();
                String ssex=textFieldSsex.getText();
                int sage=Integer.parseInt(textFieldAge.getText());
                String sxy=textFieldSxy.getText();
                Date date=Date.valueOf(textFieldTime.getText());
                String sclass=textFieldSclass.getText();
                Student stu=new Student(sno,sname,ssex,sage,sxy,date,sclass);
                boolean b = StudentDAO.doInsert(stu);
                if (b){
                    JOptionPane.showMessageDialog(null,"增加成功");
                }else {
                    JOptionPane.showMessageDialog(null,"数据格式有问题，请修改");
                }
            }else{
                JOptionPane.showMessageDialog(null,"数据有问题请修改");
            }
        });

        JButton btnExit = new JButton("退出");
        btnExit.setBounds(220, 199, 97, 23);
        getContentPane().add(btnExit);
        btnExit.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"退出成功");
            AddStudent.super.dispose();
        });



    }

    /**
     * 检查学号
     * @param name
     * @return
     */
    public int checkXh(String name){
        //检查学号

        if (name.length()==0)
            return 2;//如果为0返回2；
        if (!name.matches("\\d+")){
            return 3;
        }

        String sql="select * from student where sno="+name;
        Connection conn = new DatabaseConnection().getConnection();
        try(PreparedStatement prst = conn.prepareStatement(sql)){
            ResultSet re = prst.executeQuery();
            if (re.next()){
                return 1;//学号重复
            }else {
                return 0;//满足条件
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 检查是否满足要求
     * @return
     */
    public int check(){
        int flag=0;
        if (checkXh(textFieldXh.getText())==0 && textFieldXm.getText().length()!=0
        && textFieldAge.getText().length()!=0 && textFieldSclass.getText().length()!=0
        && textFieldSsex.getText().length()!=0 && textFieldSxy.getText().length()!=0
        && textFieldTime.getText().length()!=0){
            flag=1;
        }

        return flag;
    }

}
