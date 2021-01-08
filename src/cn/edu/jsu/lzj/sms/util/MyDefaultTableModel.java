package cn.edu.jsu.lzj.sms.util;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * 这是个MyDefautTableModel继承了类
 */
public class MyDefaultTableModel extends DefaultTableModel {

    public MyDefaultTableModel() {
    }

    public MyDefaultTableModel(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public MyDefaultTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * 使用Vector装载表格数据模型，覆写getColumnClass方法，实现按数值排序
     */
    public Class getColumnClass(int column) {
        Class returnValue;

            if ((column >= 0) && (column < getColumnCount())) {
                returnValue = getValueAt(0, column).getClass();
            } else { returnValue = Object.class; }


        return returnValue;
    }

}
