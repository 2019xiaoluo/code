package cn.edu.jsu.lzj.sms.util;


import cn.edu.jsu.lzj.sms.dao.CourseDAO;
import cn.edu.jsu.lzj.sms.dao.ScjDAO;
import cn.edu.jsu.lzj.sms.dao.StudentDAO;
import java.util.ArrayList;


/**
 * 分页的工具类
 */
public class PageUtil {
    private ArrayList bigList ; // 大集合，从外界获取数据
    private ArrayList smallList = new ArrayList<>(); // 小集合，返回给调用它的类
    private static int curentPageIndex = 1; // 当前页码
    private static int  countPerpage = 5; // 每页显示条数
    private int pageCount; // 总页数
    private int recordCount; // 总记录条数
    private String name;//记录是哪一个实体类
    private String cno;//设置是哪一个课程号
    private int start=0;
    private int end=0;//设置区间


    /**
     * 构造方法
     * @param name
     */
    public PageUtil(String name) {
        this.name=name;
        if(bigList==null) {
            if ("Student".equals(name))
                bigList= StudentDAO.getAllaccount();// 调用查询数据库的方法，返回所有的行
            else if("Course".equals(name))
                bigList= CourseDAO.getAllcount();
            else if ("Scj".equals(name)){
                bigList= ScjDAO.getAllcount();
            }

        }
        //获取总页数
        if(bigList.size()%countPerpage==0) {
            pageCount=bigList.size()/countPerpage;
        }else {
            pageCount=bigList.size()/countPerpage+1;
        }
    }

    public PageUtil(String name,int curentPageIndex,String cno) {//构造方法设置当前页
        this(name);
        this.cno=cno;
        this.curentPageIndex = curentPageIndex;
    }

    public void setCountPerpage(int countPerpage) {//设置每页显示的记录数
        this.countPerpage=countPerpage;
        //重新获取总页数
        if(bigList.size()%countPerpage==0) {
            pageCount=bigList.size()/countPerpage;
        }else {
            pageCount=bigList.size()/countPerpage+1;
        }
    }

    /**
     * 获得当前页的数据
     * @return
     */
    public ArrayList getPaegData() {// 根据当前页数，筛选记录
        if ("Student".equals(this.name))
            smallList=StudentDAO.doPage((curentPageIndex-1)*countPerpage,countPerpage);
        else if ("Course".equals(this.name))
            smallList=CourseDAO.doPage((curentPageIndex-1)*countPerpage,countPerpage);
        else if ("Scj".equals(this.name))
        {
            if (start==0 && end==0)
                smallList=ScjDAO.doPageByCno((curentPageIndex-1)*countPerpage,countPerpage,cno);
            else
                smallList=ScjDAO.doPagebyGrade((curentPageIndex-1)*countPerpage,countPerpage,start,end,cno);
        }

        return smallList;//返回小集合（当前页的数据）
    }

    /**
     * 获得下一页的数据
     * @return
     */
    public ArrayList nextPage(){//下一页
        if(curentPageIndex<pageCount) {
            curentPageIndex++;
        }else {
            curentPageIndex=1;
        }
        return getPaegData();//返回下一页的数据
    }

    /**
     * 获得上一页的数据
     * @return
     */
    public ArrayList prePage(){//上一页
        if(curentPageIndex>1) {
            curentPageIndex--;
        }else {
            curentPageIndex=pageCount;
        }
        return getPaegData();//返回上一页的数据
    }

    public void setBigList(ArrayList bigList) {
        this.bigList = bigList;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public static int getCountPerpage() {
        return countPerpage;
    }

    public void setSmallList(ArrayList smallList) {
        this.smallList = smallList;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
