package cn.edu.mju.ccce.dtrsystem.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bean.Evaluate<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-03-16 12:31<br>
 */
public class Evaluate implements Serializable {
    private static final long serialVersionUID = -4947935210818989461L;
    private long EVALUATE_ID;// int(16) NOT NULL AUTO_INCREMENT COMMENT '表ID，自增',
    private long EVALUATE_NBR;// int(16) DEFAULT NULL COMMENT '表NBR，可重复',
    private long COURSE_ID;// bigint(17) DEFAULT NULL COMMENT '表ID',
    private String COURSE_NAME;// varchar(16) DEFAULT NULL COMMENT '课程名',
    private int USER_NBR;// bigint(17) DEFAULT NULL COMMENT '用NBR',
    private String USER_NAME;// varchar(64) DEFAULT NULL COMMENT '用户名',
    private String EVALUATE_DETAIL;// longtext COMMENT '评价内容',
    private double EVALUATE_SCORE = 0;// double(2,0) DEFAULT NULL COMMENT '评价分值',
    private int EVALUATE_STATUS;// int(1) DEFAULT NULL COMMENT '评价状态',
    private Date CREAT_TIME;// datetime DEFAULT NULL COMMENT '创建时间',
    private Date UPDATE_TIME = new Date();// datetime DEFAULT NULL COMMENT '更新时间',

    public long getEVALUATE_ID() {
        return EVALUATE_ID;
    }

    public void setEVALUATE_ID(long EVALUATE_ID) {
        this.EVALUATE_ID = EVALUATE_ID;
    }

    public long getEVALUATE_NBR() {
        return EVALUATE_NBR;
    }

    public void setEVALUATE_NBR(long EVALUATE_NBR) {
        this.EVALUATE_NBR = EVALUATE_NBR;
    }

    public long getCOURSE_ID() {
        return COURSE_ID;
    }

    public void setCOURSE_ID(long COURSE_ID) {
        this.COURSE_ID = COURSE_ID;
    }

    public String getCOURSE_NAME() {
        return COURSE_NAME;
    }

    public void setCOURSE_NAME(String COURSE_NAME) {
        this.COURSE_NAME = COURSE_NAME;
    }

    public int getUSER_NBR() {
        return USER_NBR;
    }

    public void setUSER_NBR(int USER_NBR) {
        this.USER_NBR = USER_NBR;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getEVALUATE_DETAIL() {
        return EVALUATE_DETAIL;
    }

    public void setEVALUATE_DETAIL(String EVALUATE_DETAIL) {
        this.EVALUATE_DETAIL = EVALUATE_DETAIL;
    }

    public double getEVALUATE_SCORE() {
        return EVALUATE_SCORE;
    }

    public void setEVALUATE_SCORE(double EVALUATE_SCORE) {
        this.EVALUATE_SCORE = EVALUATE_SCORE;
    }

    public int getEVALUATE_STATUS() {
        return EVALUATE_STATUS;
    }

    public void setEVALUATE_STATUS(int EVALUATE_STATUS) {
        this.EVALUATE_STATUS = EVALUATE_STATUS;
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

        Evaluate evaluate = (Evaluate) o;

        if (EVALUATE_ID != evaluate.EVALUATE_ID) return false;
        if (EVALUATE_NBR != evaluate.EVALUATE_NBR) return false;
        if (COURSE_ID != evaluate.COURSE_ID) return false;
        if (USER_NBR != evaluate.USER_NBR) return false;
        if (Double.compare(evaluate.EVALUATE_SCORE, EVALUATE_SCORE) != 0) return false;
        if (EVALUATE_STATUS != evaluate.EVALUATE_STATUS) return false;
        if (COURSE_NAME != null ? !COURSE_NAME.equals(evaluate.COURSE_NAME) : evaluate.COURSE_NAME != null)
            return false;
        if (USER_NAME != null ? !USER_NAME.equals(evaluate.USER_NAME) : evaluate.USER_NAME != null) return false;
        if (EVALUATE_DETAIL != null ? !EVALUATE_DETAIL.equals(evaluate.EVALUATE_DETAIL) : evaluate.EVALUATE_DETAIL != null)
            return false;
        if (CREAT_TIME != null ? !CREAT_TIME.equals(evaluate.CREAT_TIME) : evaluate.CREAT_TIME != null) return false;
        return UPDATE_TIME != null ? UPDATE_TIME.equals(evaluate.UPDATE_TIME) : evaluate.UPDATE_TIME == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (EVALUATE_ID ^ (EVALUATE_ID >>> 32));
        result = 31 * result + (int) (EVALUATE_NBR ^ (EVALUATE_NBR >>> 32));
        result = 31 * result + (int) (COURSE_ID ^ (COURSE_ID >>> 32));
        result = 31 * result + (COURSE_NAME != null ? COURSE_NAME.hashCode() : 0);
        result = 31 * result + USER_NBR;
        result = 31 * result + (USER_NAME != null ? USER_NAME.hashCode() : 0);
        result = 31 * result + (EVALUATE_DETAIL != null ? EVALUATE_DETAIL.hashCode() : 0);
        temp = Double.doubleToLongBits(EVALUATE_SCORE);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + EVALUATE_STATUS;
        result = 31 * result + (CREAT_TIME != null ? CREAT_TIME.hashCode() : 0);
        result = 31 * result + (UPDATE_TIME != null ? UPDATE_TIME.hashCode() : 0);
        return result;
    }
}
