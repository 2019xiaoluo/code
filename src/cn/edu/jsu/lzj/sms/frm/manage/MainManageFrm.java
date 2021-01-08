package cn.edu.jsu.lzj.sms.frm.manage;




import cn.edu.jsu.lzj.sms.frm.LoginFrm;
import cn.edu.jsu.lzj.sms.vo.AccountNum;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * 管理员的主窗口
 */

public class MainManageFrm extends JDialog {


    private static MainManageFrm instance=null;
    public static MainManageFrm getInstance(){
        if (instance==null){
            instance=new MainManageFrm();
        }
        return instance;
    }


    /**
     * Create the dialog.
     */
    private MainManageFrm() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java\\code\\src\\cn\\edu\\jsu\\lzj\\sms\\source\\e13.png"));

        //设置背景图
        ImageIcon img=new ImageIcon("D:\\java\\code\\src\\cn\\edu\\jsu\\lzj\\sms\\source\\apic130941.jpg");
        JLabel lable=new JLabel(img);
        this.getLayeredPane().setLayout(null);
        lable.setBounds(0, 0, 1000, 532);
        this.getLayeredPane().add(lable,new Integer(Integer.MIN_VALUE));
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);
        setTitle("管理端界面");

        setBounds(100, 100, 1000, 532);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("欢迎你登录学生信息管理系统管理端");
        lblNewLabel.setBounds(0, 90, 986, 61);
        lblNewLabel.setFont(new Font("Adobe 宋体 Std L", Font.BOLD, 30));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("亲爱的管理员");
        lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 24));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(0, 161, 976, 61);
        getContentPane().add(lblNewLabel_1);


        //从文件中读出账号信息
        AccountNum accountNum=null;
        File file=new File("D:\\java\\code\\src\\cn\\edu\\jsu\\lzj\\sms\\source\\当前登录的账号.txt");
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));){
            Object o = ois.readObject();
            accountNum=(AccountNum) o;
        }catch (Exception e){
            e.printStackTrace();
        }


        String name=accountNum.getId().toString();
        JLabel lblNewLabel_2 = new JLabel(name);
        lblNewLabel_2.setFont(new Font("Adobe 宋体 Std L", Font.BOLD | Font.ITALIC, 33));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(0, 256, 976, 73);
        getContentPane().add(lblNewLabel_2);

        JButton btnNewButton = new JButton("退出系统");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainManageFrm.super.setVisible(false);
                JOptionPane.showMessageDialog(null,"退出成功");
                LoginFrm.close();
            }
        });

        btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
        btnNewButton.setBounds(400, 339, 195, 33);
        getContentPane().add(btnNewButton);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnStudentlMessage = new JMenu("个人信息");
        menuBar.add(mnStudentlMessage);

        JMenuItem mntmLookStuMessage = new JMenuItem("浏览学生信息");
        mntmLookStuMessage.setHorizontalAlignment(SwingConstants.CENTER);
        mnStudentlMessage.add(mntmLookStuMessage);
        mntmLookStuMessage.addActionListener(e->{
            ShowStudentMessage sfrm=ShowStudentMessage.getInstace();
            sfrm.setVisible(true);
        });

        JSeparator separator = new JSeparator();
        mnStudentlMessage.add(separator);

        JMenuItem mntmAddMessage = new JMenuItem("增加学生");
        mntmAddMessage.setHorizontalAlignment(SwingConstants.CENTER);
        mnStudentlMessage.add(mntmAddMessage);
        mntmAddMessage.addActionListener(e->{
            AddStudent astu=AddStudent.getInstance();
            astu.setVisible(true);
        });

        JMenu mnCourseMessage = new JMenu("课程信息");
        menuBar.add(mnCourseMessage);

        JMenuItem mntmLookCourse = new JMenuItem("查看课程信息");
        mntmLookCourse.setHorizontalAlignment(SwingConstants.CENTER);
        mnCourseMessage.add(mntmLookCourse);
        mntmLookCourse.addActionListener(e->{
            ShowCourseMessage courefrm=ShowCourseMessage.getInstance();
            courefrm.setVisible(true);
        });

        JSeparator separator_3 = new JSeparator();
        mnCourseMessage.add(separator_3);

        JMenuItem mntmAddCourse = new JMenuItem("增加课程");
        mntmAddCourse.setHorizontalAlignment(SwingConstants.CENTER);
        mnCourseMessage.add(mntmAddCourse);
        mntmAddCourse.addActionListener(e->{
            AddCourse acourse=AddCourse.getInstance();
            acourse.setVisible(true);
        });

        JMenu mnTongji = new JMenu("统计");
        menuBar.add(mnTongji);

        JMenuItem mntmLook = new JMenuItem("打开分析成绩");
        mnTongji.add(mntmLook);
        mntmLook.addActionListener(e->{
            ShowScjMessage showScjMessage=ShowScjMessage.getInstance();
            showScjMessage.setVisible(true);
        });
    }
}

