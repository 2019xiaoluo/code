package cn.edu.jsu.lzj.sms.frm.manage;

import cn.edu.jsu.lzj.sms.dao.CourseDAO;
import cn.edu.jsu.lzj.sms.dao.StudentDAO;
import cn.edu.jsu.lzj.sms.util.MyDefaultTableModel;
import cn.edu.jsu.lzj.sms.util.PageUtil;
import cn.edu.jsu.lzj.sms.vo.Course;
import cn.edu.jsu.lzj.sms.vo.Student;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * 显示课程信息的窗口
 */
public class ShowCourseMessage extends JDialog {


	private JTextField textKey;
	private JTable table;
	private TableRowSorter sorter;
	private DefaultTableModel model;
	private PageUtil pageUtil;
	private static ShowCourseMessage instance=null;

	public static ShowCourseMessage getInstance(){
		if (instance==null){
			instance=new ShowCourseMessage();
		}
		return instance;
	}


	/**
	 * Create the dialog.
	 */
	private ShowCourseMessage() {
		setTitle("课程信息");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Vector<String> titles=new Vector<>();
		Collections.addAll(titles, "课程号","课程名","授课老师","学分");

		setBounds(100, 100, 924, 540);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		textKey = new JTextField();
		textKey.setBounds(157, 38, 123, 21);
		getContentPane().add(textKey);
		textKey.setColumns(10);

		JLabel lblKey = new JLabel("关键字：");
		lblKey.setBounds(61, 41, 58, 15);
		getContentPane().add(lblKey);

		JButton btnCX = new JButton("查询");
		btnCX.addActionListener(e-> {
			//查询时修改Model为全部数据
			model=new MyDefaultTableModel(titles,0);
			ArrayList<Course> allaccount = CourseDAO.getAllcount();
			for (Course s : allaccount) {
				Vector row=new Vector();
				row.add(s.getCno());
				row.add(s.getCname());
				row.add(s.getcTeacher());
				row.add(s.getCredit());
				model.addRow(row);
			}
			table.setModel(model);//设置表格的数据模型
			check(textKey.getText());
		});
		btnCX.setBounds(315, 37, 97, 23);
		getContentPane().add(btnCX);

		JButton btnUPdate = new JButton("修改");
		btnUPdate.addActionListener(e->update());
		btnUPdate.setBounds(434, 37, 97, 23);
		getContentPane().add(btnUPdate);

		JButton btnDelete = new JButton("删除");
		btnDelete.addActionListener(e->delete());
		btnDelete.setBounds(551, 37, 97, 23);
		getContentPane().add(btnDelete);

		JButton btnSort = new JButton("排序");
		btnSort.addActionListener(e->sort());
		btnSort.setBounds(681, 37, 97, 23);
		getContentPane().add(btnSort);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 115, 778, 300);
		getContentPane().add(scrollPane);

		model=init();
		table = new JTable(model);
		scrollPane.setViewportView(table);

		//进行分页
		pageUtil=new PageUtil("Course");

		JButton btnpre=new JButton("上一页");
		btnpre.setBounds(200,425,97,23);
		getContentPane().add(btnpre);
		btnpre.addActionListener(e -> {
			table.setRowSorter(null);
			model=new MyDefaultTableModel(titles,0);
			ArrayList small=pageUtil.prePage();
			for (Object o : small) {
				Course c=(Course) o;
				Vector row=new Vector();
				row.add(c.getCno());
				row.add(c.getCname());
				row.add(c.getcTeacher());
				row.add(c.getCredit());
				model.addRow(row);
			}
			//设置数据模型中的数据为上一页内容
			table.setModel(model);//设置表格的数据模型

		});

		JButton btnnext=new JButton("下一页");
		btnnext.setBounds(320,425,97,23);
		getContentPane().add(btnnext);
		btnnext.addActionListener(e->{
			table.setRowSorter(null);
			model=new MyDefaultTableModel(titles,0);
			ArrayList small=pageUtil.nextPage();
			for (Object o : small) {
				Course c=(Course) o;
				Vector row=new Vector();
				row.add(c.getCno());
				row.add(c.getCname());
				row.add(c.getcTeacher());
				row.add(c.getCredit());
				model.addRow(row);
			}
			;//设置数据模型中的数据为下一页内容
			table.setModel(model);//设置表格的数据模型
		});

		JLabel jLabelshow=new JLabel("每页显示：");
		jLabelshow.setBounds(430,425,60,23);
		getContentPane().add(jLabelshow);

		Vector<Integer> v=new Vector<>();
		Collections.addAll(v,5,10,15,20);
		JComboBox<Integer> jc=new JComboBox<>(v);
		jc.setBounds(500,425,50,23);
		jc.addItemListener(e -> {
			table.setRowSorter(null);
			int pageSize=Integer.valueOf(jc.getSelectedItem().toString());//获取下拉框所选的值
			pageUtil.setCountPerpage(pageSize);
			model=new MyDefaultTableModel(titles,0);
			ArrayList small=pageUtil.getPaegData();
			for (Object o : small) {
				Course c=(Course) o;
				Vector row=new Vector();
				row.add(c.getCno());
				row.add(c.getCname());
				row.add(c.getcTeacher());
				row.add(c.getCredit());
				model.addRow(row);
			}

			table.setModel(model);//设置表格数据模型

		});
		jc.setSelectedIndex(0);
		getContentPane().add(jc);



	}

	/**
	 * 初始化model
	 * @return
	 */
	public DefaultTableModel init(){
		//初始化表格
		Vector<String> titles=new Vector<>();
		Collections.addAll(titles, "课程号","课程名","授课老师","学分");
		DefaultTableModel tableModel=new DefaultTableModel(titles,0);//使用Vector装载表格数据模型，覆写getColumnClass方法，实现按数值排序
		final ArrayList<Course> allcount = CourseDAO.doPage(0,5);//默认开始0-5显示
		for (Course c : allcount) {
			Vector row=new Vector();
			row.add(c.getCno());
			row.add(c.getCname());
			row.add(c.getcTeacher());
			row.add(c.getCredit());
			tableModel.addRow(row);
		}

		return tableModel;
	}

	/**
	 * 检查
	 * @param name
	 */
	public void check(String name){
		//查询方法
		sorter=new TableRowSorter(model);
		sorter.setRowFilter(RowFilter.regexFilter(name+".*"));
		table.setRowSorter(sorter);
	}

	/**
	 * 获得一个课程
	 * @param i
	 * @return
	 */
	public Course getCourse(int i){
		//获取每一行构建成Course对象
		String cno=(String)model.getValueAt(i,0);
		String cname=(String)model.getValueAt(i,1);
		String cTeacher=(String)model.getValueAt(i,2);
		Double credit=Double.parseDouble(model.getValueAt(i,3).toString()) ;

		Course c=new Course(cno,cname,cTeacher,credit);
		return c;
	}

	/**
	 * 执行更新操作
	 */
	public void update(){
		//更新的方法
		if (table.getSelectedRow()!=-1 && table.getSelectedColumn()!=0){//默认不能修改学号
			if (JOptionPane.showConfirmDialog(null,"确定修改吗？(更新到数据库)","",JOptionPane.YES_NO_OPTION)==0){
				CourseDAO.doUpdate(getCourse(table.getSelectedRow()));//从数据库中修改同步数据库
			}
		}
		else if(table.getSelectedRow()==-1)
		{
			JOptionPane.showMessageDialog(null,"请选择要修改的行！");
		}else if (table.getSelectedColumn()!=0){
			JOptionPane.showMessageDialog(null,"不能修改课程号！");
		}
	}

	/**
	 * 删除操作
	 */
	public void delete(){
		//删除某一行的方法
		if (table.getSelectedRow()!=-1){
			if (JOptionPane.showConfirmDialog(null,"确定删除吗？","",JOptionPane.YES_NO_OPTION)==0){
				CourseDAO.doDelete(getCourse(table.getSelectedRow()));//从数据库中删除

				//从展示的地方删除
				model.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"请选择要删除的行！");
		}
	}

	/**
	 * 排序
	 */
	public void sort() {
		//排序功能实现
		JOptionPane.showMessageDialog(null,"按数值课程号进行排序");
		sorter = new TableRowSorter<DefaultTableModel>(model);//设置排序器
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		table.setRowSorter(sorter);
	}
}
