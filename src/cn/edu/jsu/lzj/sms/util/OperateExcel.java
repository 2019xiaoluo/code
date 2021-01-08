package cn.edu.jsu.lzj.sms.util;


import cn.edu.jsu.lzj.sms.dao.CourseDAO;
import cn.edu.jsu.lzj.sms.dao.ScjDAO;
import cn.edu.jsu.lzj.sms.dao.StudentDAO;
import cn.edu.jsu.lzj.sms.vo.Scj;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 操作Excel的类
 */
public class OperateExcel {
    /**
     * 获取excel文档对象，参数为指定excel文件
     */

    public static Workbook getWorkbook(File file) throws IOException {
        Workbook wb=null;
        if(file.getName().endsWith("xls")) {//根据excel的类型选择不同的子类实例化文档对象
            wb=new HSSFWorkbook(new FileInputStream(file));
        }else if(file.getName().endsWith("xlsx")) {
            wb=new XSSFWorkbook(new FileInputStream(file));
        }
        return wb;
    }

    /**
     * 从ArrayList<Scj>增加到Excel
     * @param file
     * @param alist
     * @return
     */
    public static boolean addExcel(File file, ArrayList<Scj> alist){
        //读文件到excel;
        boolean flag=false;
        try(Workbook wb = getWorkbook(file);
            FileOutputStream fos=new FileOutputStream(file)){
            Sheet sheet = wb.getSheetAt(0);
            for (Scj scj : alist) {
                Row row = sheet.createRow(sheet.getPhysicalNumberOfRows() + 1);
                row.createCell(0).setCellValue(scj.getCno());
                row.createCell(1).setCellValue(scj.getSno().toString());
                row.createCell(2).setCellValue(StudentDAO.findNameBySno(scj.getSno()));
                row.createCell(3).setCellValue(scj.getGrade().toString());
            }
            wb.write(fos);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 导入数据
     * @param alist
     * @return
     */
    public static boolean jfChoose(ArrayList<Scj> alist) {
        boolean flag=false;
        JFileChooser jfc=new JFileChooser(new File("D:\\"));
        jfc.setDialogTitle("导出数据");
        jfc.setApproveButtonText("选择Excel文件");
        jfc.setAcceptAllFileFilterUsed(false);//设置文件类型为全部文件不能用，可选
        FileNameExtensionFilter filter = new FileNameExtensionFilter("excel文件(*.xls,*.xlsx)", "xls","xlsx"); //限制文件只能显示xls\xlsx格式的文件
        jfc.addChoosableFileFilter(filter);//增加文件过滤，可选
        int i = jfc.showOpenDialog(null);
        if (i==JFileChooser.APPROVE_OPTION){
            File file =jfc.getSelectedFile();
            //到Excel
            flag=addExcel(file,alist);
            try {
                Workbook wb=getWorkbook(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 导入数据
     * @return
     */
    public static boolean jfChoose() {
        boolean flag=false;
        JFileChooser jfc=new JFileChooser(new File("D:\\"));
        jfc.setDialogTitle("导入数据");
        jfc.setApproveButtonText("选择Excel文件");
        jfc.setAcceptAllFileFilterUsed(false);//设置文件类型为全部文件不能用，可选
        FileNameExtensionFilter filter = new FileNameExtensionFilter("excel文件(*.xls,*.xlsx)", "xls","xlsx"); //限制文件只能显示xls\xlsx格式的文件
        jfc.addChoosableFileFilter(filter);//增加文件过滤，可选
        int i = jfc.showOpenDialog(null);
        if (i==JFileChooser.APPROVE_OPTION){
            File file =jfc.getSelectedFile();
            System.out.println(file);
            Workbook wb= null;
            try {
                wb = getWorkbook(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //加入数据库
            Sheet sheet = wb.getSheetAt(0);
            for (int j=0;j<sheet.getPhysicalNumberOfRows();j++){
                System.out.println("33333");
                Row row = sheet.getRow(j);
                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);
                Cell cell3 = row.getCell(2);
                cell1.setCellType(CellType.STRING);
                cell2.setCellType(CellType.STRING);
                cell3.setCellType(CellType.STRING);
                String cno=cell1.toString();
                Integer sno=Integer.parseInt(cell2.toString());
                Double grade=Double.parseDouble(cell3.toString());
                System.out.println(cno+" "+sno+" "+grade);
                //先判断学号课程号是否存在
                if (StudentDAO.findNameBySno(sno)!=null && CourseDAO.findNameByCno(cno)!=null){
                    Scj scj=new Scj(cno,sno,grade);
                    flag=ScjDAO.doInsert(scj);
                }
            }
        }
        return flag;

    }
}
