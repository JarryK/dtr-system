package cn.edu.mju.ccce.dtrsystem.bean;

import javax.jnlp.ServiceManagerStub;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bean.Issue<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-15 19:44<br>
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 8068995269823104033L;
    private BigInteger COURSE_ID;//表ID
    private String COURSE_NAME;//课程名
    private int COURSE_TYPE_ID;//类型id
    private String COURSE_TYPE_NAME;//类型名
    private String COURSE_DETAIL;//发布课程内容
    private String COURSE_TEACHER_NAME;//任课老师名
    private int COURSE_STU_NBR;//课程学生限制人数
    private Date COURSE_TIME;//发布课程时间
    private BigInteger EVALUATE_NBR;//评价nbr
    private int COURSE_STATUS = 0;//发布状态 0=可以预约 1=已结束预约 2=异常
    private Date CREAT_TIME;//创建时间
    private Date UPDATE_TIME = new Date();//更新时间

    public BigInteger getCOURSE_ID() {
        return COURSE_ID;
    }

    public void setCOURSE_ID(BigInteger COURSE_ID) {
        this.COURSE_ID = COURSE_ID;
    }

    public String getCOURSE_NAME() {
        return COURSE_NAME;
    }

    public void setCOURSE_NAME(String COURSE_NAME) {
        this.COURSE_NAME = COURSE_NAME;
    }

    public int getCOURSE_TYPE_ID() {
        return COURSE_TYPE_ID;
    }

    public void setCOURSE_TYPE_ID(int COURSE_TYPE_ID) {
        this.COURSE_TYPE_ID = COURSE_TYPE_ID;
    }

    public String getCOURSE_TYPE_NAME() {
        return COURSE_TYPE_NAME;
    }

    public void setCOURSE_TYPE_NAME(String COURSE_TYPE_NAME) {
        this.COURSE_TYPE_NAME = COURSE_TYPE_NAME;
    }

    public String getCOURSE_DETAIL() {
        return COURSE_DETAIL;
    }

    public void setCOURSE_DETAIL(String COURSE_DETAIL) {
        this.COURSE_DETAIL = COURSE_DETAIL;
    }

    public String getCOURSE_TEACHER_NAME() {
        return COURSE_TEACHER_NAME;
    }

    public void setCOURSE_TEACHER_NAME(String COURSE_TEACHER_NAME) {
        this.COURSE_TEACHER_NAME = COURSE_TEACHER_NAME;
    }

    public int getCOURSE_STU_NBR() {
        return COURSE_STU_NBR;
    }

    public void setCOURSE_STU_NBR(int COURSE_STU_NBR) {
        this.COURSE_STU_NBR = COURSE_STU_NBR;
    }

    public Date getCOURSE_TIME() {
        return COURSE_TIME;
    }

    public void setCOURSE_TIME(Date COURSE_TIME) {
        this.COURSE_TIME = COURSE_TIME;
    }

    public BigInteger getEVALUATE_NBR() {
        return EVALUATE_NBR;
    }

    public void setEVALUATE_NBR(BigInteger EVALUATE_NBR) {
        this.EVALUATE_NBR = EVALUATE_NBR;
    }

    public int getCOURSE_STATUS() {
        return COURSE_STATUS;
    }

    public void setCOURSE_STATUS(int COURSE_STATUS) {
        this.COURSE_STATUS = COURSE_STATUS;
    }

    public Date getCREAT_TIME() {
        return CREAT_TIME;
    }

    public void setCREAT_TIME(Date CREAT_TIME) {
        this.CREAT_TIME = CREAT_TIME;
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (COURSE_TYPE_ID != course.COURSE_TYPE_ID) return false;
        if (COURSE_STU_NBR != course.COURSE_STU_NBR) return false;
        if (COURSE_STATUS != course.COURSE_STATUS) return false;
        if (COURSE_ID != null ? !COURSE_ID.equals(course.COURSE_ID) : course.COURSE_ID != null) return false;
        if (COURSE_NAME != null ? !COURSE_NAME.equals(course.COURSE_NAME) : course.COURSE_NAME != null) return false;
        if (COURSE_TYPE_NAME != null ? !COURSE_TYPE_NAME.equals(course.COURSE_TYPE_NAME) : course.COURSE_TYPE_NAME != null)
            return false;
        if (COURSE_DETAIL != null ? !COURSE_DETAIL.equals(course.COURSE_DETAIL) : course.COURSE_DETAIL != null)
            return false;
        if (COURSE_TEACHER_NAME != null ? !COURSE_TEACHER_NAME.equals(course.COURSE_TEACHER_NAME) : course.COURSE_TEACHER_NAME != null)
            return false;
        if (COURSE_TIME != null ? !COURSE_TIME.equals(course.COURSE_TIME) : course.COURSE_TIME != null) return false;
        if (EVALUATE_NBR != null ? !EVALUATE_NBR.equals(course.EVALUATE_NBR) : course.EVALUATE_NBR != null)
            return false;
        if (CREAT_TIME != null ? !CREAT_TIME.equals(course.CREAT_TIME) : course.CREAT_TIME != null) return false;
        return UPDATE_TIME != null ? UPDATE_TIME.equals(course.UPDATE_TIME) : course.UPDATE_TIME == null;
    }

    @Override
    public int hashCode() {
        int result = COURSE_ID != null ? COURSE_ID.hashCode() : 0;
        result = 31 * result + (COURSE_NAME != null ? COURSE_NAME.hashCode() : 0);
        result = 31 * result + COURSE_TYPE_ID;
        result = 31 * result + (COURSE_TYPE_NAME != null ? COURSE_TYPE_NAME.hashCode() : 0);
        result = 31 * result + (COURSE_DETAIL != null ? COURSE_DETAIL.hashCode() : 0);
        result = 31 * result + (COURSE_TEACHER_NAME != null ? COURSE_TEACHER_NAME.hashCode() : 0);
        result = 31 * result + COURSE_STU_NBR;
        result = 31 * result + (COURSE_TIME != null ? COURSE_TIME.hashCode() : 0);
        result = 31 * result + (EVALUATE_NBR != null ? EVALUATE_NBR.hashCode() : 0);
        result = 31 * result + COURSE_STATUS;
        result = 31 * result + (CREAT_TIME != null ? CREAT_TIME.hashCode() : 0);
        result = 31 * result + (UPDATE_TIME != null ? UPDATE_TIME.hashCode() : 0);
        return result;
    }
}