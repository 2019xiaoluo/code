package cn.edu.jsu.lzj.sms.frm.manage;

import cn.edu.jsu.lzj.sms.dao.CourseDAO;
import cn.edu.jsu.lzj.sms.dao.ScjDAO;
import cn.edu.jsu.lzj.sms.dao.StudentDAO;
import cn.edu.jsu.lzj.sms.util.MyDefaultTableModel;
import cn.edu.jsu.lzj.sms.util.OperateExcel;
import cn.edu.jsu.lzj.sms.util.PageUtil;
import cn.edu.jsu.lzj.sms.vo.Course;
import cn.edu.jsu.lzj.sms.vo.Scj;
import cn.edu.jsu.lzj.sms.vo.Student;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * 这是个显示分析成绩的界面
 */
public class ShowScjMessage extends JDialog {

	private JTextField textFieldXh;
	private JTextField textFieldStart;
	private JTextField textFieldEnd;
	private JTable table;
	private TableRowSorter sorter;
	private DefaultTableModel model;
	private PageUtil pageUtil;
	private JComboBox<Integer> jc;
	private static ShowScjMessage instance=null;
	public static ShowScjMessage getInstance(){
		if (instance==null){
			instance=new ShowScjMessage();
		}
		return instance;
	}

	/**
	 * Create the dialog.
	 */
	private ShowScjMessage() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Vector<String> titles=new Vector<>();
		Collections.addAll(titles, "课程号","学号","成绩");


		setTitle("学生课程统计与查看");
		setBounds(100, 100, 924, 540);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel jLabelshow=new JLabel("课程号：");
		jLabelshow.setBounds(71,34,60,23);
		getContentPane().add(jLabelshow);

		ArrayList<String> allCno = CourseDAO.getAllCno();
		Vector<String> v=new Vector<>(allCno);

		JComboBox<String> j=new JComboBox<>(v);
		j.setBounds(191,34,100,23);
		j.addItemListener(e -> {
			table.setRowSorter(null);//为了防止sorter影响效果
			model = showByCno(j.getSelectedItem().toString());
			pageUtil.setBigList(ScjDAO.getAllbyCno(j.getSelectedItem().toString()));
			pageUtil.setCountPerpage(Integer.valueOf(jc.getSelectedItem().toString()));
			pageUtil.setCno(j.getSelectedItem().toString());
			table.setModel(model);
		});
		j.setSelectedIndex(0);//设置默认第一个
		getContentPane().add(j);



		JLabel lblNewLabel = new JLabel("学号：");
		lblNewLabel.setBounds(429, 37, 58, 15);
		getContentPane().add(lblNewLabel);

		textFieldXh = new JTextField();
		textFieldXh.setBounds(473, 35, 161, 21);
		getContentPane().add(textFieldXh);
		textFieldXh.setColumns(10);

		JButton btnCx = new JButton("查询");
		btnCx.setBounds(697, 34, 141, 23);
		getContentPane().add(btnCx);
		//实现按学号查询
		btnCx.addActionListener(e->{
			if (textFieldXh.getText().length()!=0){
				//查询时修改Model为全部数据
				model=getTableModel(ScjDAO.getAllbyCno(j.getSelectedItem().toString()));
				//查询当前课程下的同学的信息
				table.setModel(model);//设置表格的数据模型
				cXBySno(textFieldXh.getText());
				textFieldXh.setText("");
			}else
			{
				JOptionPane.showMessageDialog(null,"学号不能为空");
			}

		});


		JLabel lblCjqj = new JLabel("成绩区间：");
		lblCjqj.setBounds(71, 91, 72, 15);
		getContentPane().add(lblCjqj);

		textFieldStart = new JTextField();
		textFieldStart.setBounds(182, 88, 60, 21);
		getContentPane().add(textFieldStart);
		textFieldStart.setColumns(10);

		JLabel lblNewLabelm = new JLabel("———");
		lblNewLabelm.setBounds(252, 91, 39, 15);
		getContentPane().add(lblNewLabelm);

		textFieldEnd = new JTextField();
		textFieldEnd.setBounds(301, 88, 60, 21);
		getContentPane().add(textFieldEnd);
		textFieldEnd.setColumns(10);

		JButton btnCxcj = new JButton("按区间查询");
		btnCxcj.setBounds(390, 87, 97, 23);
		getContentPane().add(btnCxcj);
		btnCxcj.addActionListener(e->{
			if (textFieldStart.getText().length()!=0&&textFieldEnd.getText().length()!=0)
			{
				//定义查询成绩区间的方法
				table.setRowSorter(null);//为了防止sorter影响效果
				model = cXByQujian(Integer.valueOf(textFieldStart.getText()),Integer.valueOf(textFieldEnd.getText()),
						j.getSelectedItem().toString());
				pageUtil.setBigList(ScjDAO.getAllbyGrade(Integer.valueOf(textFieldStart.getText()),
						Integer.valueOf(textFieldEnd.getText()),
						j.getSelectedItem().toString()));

				pageUtil.setStart(Integer.valueOf(textFieldStart.getText()));
				pageUtil.setEnd(Integer.valueOf(textFieldEnd.getText()));//设置区间

				pageUtil.setCountPerpage(Integer.valueOf(jc.getSelectedItem().toString()));
				pageUtil.setCno(j.getSelectedItem().toString());
				table.setModel(model);
			}
			else {
				JOptionPane.showMessageDialog(null,"区间不能为空");
			}



		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 144, 816, 265);
		getContentPane().add(scrollPane);

		model=init(j.getSelectedItem().toString());
		table = new JTable(model);
		scrollPane.setViewportView(table);

		pageUtil=new PageUtil("Scj",1,j.getSelectedItem().toString());//进行分页

		JButton btnpre=new JButton("上一页");
		btnpre.setBounds(200,425,97,23);
		getContentPane().add(btnpre);
		btnpre.addActionListener(e -> {
			table.setRowSorter(null);//为了防止sorter影响分页
			model=new MyDefaultTableModel(titles,0);
			ArrayList small=pageUtil.prePage();
			for (Object o : small) {
				Scj s=(Scj) o;
				Vector row=new Vector();
				row.add(s.getCno());
				row.add(s.getSno());
				row.add(s.getGrade());
				model.addRow(row);
			}
			//设置数据模型中的数据为上一页内容
			table.setModel(model);//设置表格的数据模型

		});

		JButton btnnext=new JButton("下一页");
		btnnext.setBounds(349,425,97,23);
		getContentPane().add(btnnext);
		btnnext.addActionListener(e->{
			table.setRowSorter(null);
			model=new MyDefaultTableModel(titles,0);
			ArrayList small=pageUtil.nextPage();
			for (Object o : small) {
				Scj s=(Scj) o;
				Vector row=new Vector();
				row.add(s.getCno());
				row.add(s.getSno());
				row.add(s.getGrade());
				model.addRow(row);
			}
			//设置数据模型中的数据为下一页内容
			table.setModel(model);//设置表格的数据模型
		});

		JLabel jLabelshow1=new JLabel("每页显示：");
		jLabelshow1.setBounds(496,425,60,23);
		getContentPane().add(jLabelshow1);

		Vector<Integer> v1=new Vector<>();
		Collections.addAll(v1,5,10,15,20);
		jc=new JComboBox<>(v1);
		jc.setBounds(588,425,72,23);
		jc.addItemListener(e -> {
			table.setRowSorter(null);
			int pageSize=Integer.valueOf(jc.getSelectedItem().toString());//获取下拉框所选的值
			pageUtil.setCountPerpage(pageSize);
			model=new MyDefaultTableModel(titles,0);
			ArrayList small=pageUtil.getPaegData();
			for (Object o : small) {
				Scj s=(Scj) o;
				Vector row=new Vector();
				row.add(s.getCno());
				row.add(s.getSno());
				row.add(s.getGrade());
				model.addRow(row);
			}
			//设置数据模型中的数据为当前一页内容
			table.setModel(model);//设置表格的数据模型

		});
		jc.setSelectedIndex(0);
		getContentPane().add(jc);



		JLabel lblTj = new JLabel("请选择统计量：");
		lblTj.setBounds(514, 91, 120, 15);
		getContentPane().add(lblTj);

		Vector<String> v2=new Vector<>();
		Collections.addAll(v2,"最大值","最小值","平均值","合计人");
		JComboBox<String> tj=new JComboBox<>(v2);
		tj.setBounds(617,87,100,23);
		tj.addItemListener(e -> {
			String s=tj.getSelectedItem().toString();
			if ("最大值".equals(s)){
				ArrayList<Scj> max = ScjDAO.getMaxGradeByCno(j.getSelectedItem().toString());
				model = getTableModel(max);
				table.setModel(model);
			}else if ("最小值".equals(s)){
				ArrayList<Scj> min = ScjDAO.getMinGradeByCno(j.getSelectedItem().toString());
				model = getTableModel(min);
				table.setModel(model);
			}else if ("平均值".equals(s)){
				String ss=ScjDAO.getAvgGradeByCno(j.getSelectedItem().toString()).get(0).toString();
				JOptionPane.showMessageDialog(null,"该课程"+j.getSelectedItem().toString()+"平均分："+ss);
			}else if ("合计人".equals(s)){
				String ss=ScjDAO.getTotalGradeByCno(j.getSelectedItem().toString()).get(0).toString();
				JOptionPane.showMessageDialog(null,"该课程"+j.getSelectedItem().toString()+"一共有："+ss);
			}
		});
		tj.setSelectedIndex(0);
		getContentPane().add(tj);


		JButton btnDaoChuExcel=new JButton("导出Excel");
		btnDaoChuExcel.addActionListener(e->{
			ArrayList<Scj> allbyCno = ScjDAO.getAllbyCno(j.getSelectedItem().toString());
			if (JOptionPane.showConfirmDialog(null,"确定导出吗？","",JOptionPane.YES_NO_OPTION)==0)
			{
				if(OperateExcel.jfChoose(allbyCno)){
					JOptionPane.showMessageDialog(null,"导出成功");
				}else{
					JOptionPane.showMessageDialog(null,"导出失败，请检查");
				}
			}
		});
		btnDaoChuExcel.setBounds(700,425,100,23);
		getContentPane().add(btnDaoChuExcel);

		JButton btnDaoRuExcel=new JButton("导入Excel");
		btnDaoRuExcel.addActionListener(e->{
			if (JOptionPane.showConfirmDialog(null,"确定导入吗？","",JOptionPane.YES_NO_OPTION)==0)
			{
				if(OperateExcel.jfChoose()){
					JOptionPane.showMessageDialog(null,"导入成功");
				}else{
					JOptionPane.showMessageDialog(null,"导入失败，请检查");
				}
			}
		});
		btnDaoRuExcel.setBounds(60,425,100,23);
		getContentPane().add(btnDaoRuExcel);

	}

	/**
	 * 根据cno返回一个model
	 * @param cc
	 * @return
	 */
	private MyDefaultTableModel showByCno(String cc) {
		//按课程号展示成绩，返回
		ArrayList<Scj> allaccount =ScjDAO.doPageByCno(0,Integer.valueOf(jc.getSelectedItem().toString()),cc);
		return getTableModel(allaccount);
	}

	/**
	 * 根据sno
	 * @param sno
	 */
	private void cXBySno(String sno){
		//根据学号返回数据
		sorter=new TableRowSorter(model);
		sorter.setRowFilter(RowFilter.regexFilter(sno+".*"));
		table.setRowSorter(sorter);

	}

	/**
	 * 根据指定区间返回指定模型
	 * @param start
	 * @param end
	 * @param cno
	 * @return
	 */
	private MyDefaultTableModel cXByQujian(int start,int end,String cno){
		ArrayList<Scj> scjs = ScjDAO.doPagebyGrade(0,Integer.valueOf(jc.getSelectedItem().toString()), start, end, cno);
		return getTableModel(scjs);
	}

	/**
	 * 初始化
	 * @param cno
	 * @return
	 */
	public MyDefaultTableModel init(String cno){
		//初始化表格
		ArrayList<Scj> allaccount = ScjDAO.doPageByCno(0,5,cno);
		return getTableModel(allaccount);

	}

	/**
	 * 根据集合返回一个model
	 * @param allaccount
	 * @return
	 */
	public MyDefaultTableModel getTableModel(ArrayList<Scj> allaccount){
		Vector<String> titles=new Vector<>();
		Collections.addAll(titles, "课程号","课程名","学号","姓名","成绩");
		MyDefaultTableModel tableModel=new MyDefaultTableModel(titles,0);
		for (Scj s : allaccount) {
			Vector row=new Vector();
			row.add(s.getCno());
			row.add(CourseDAO.findNameByCno(s.getCno()));
			row.add(s.getSno());
			row.add(StudentDAO.findNameBySno(s.getSno()));
			row.add(s.getGrade());
			tableModel.addRow(row);
		}
		return tableModel;
	}


}
