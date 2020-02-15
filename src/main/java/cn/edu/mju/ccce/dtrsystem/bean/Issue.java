package cn.edu.mju.ccce.dtrsystem.bean;

import javax.jnlp.ServiceManagerStub;
import java.io.Serializable;
import java.util.Date;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bean.Issue<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-15 19:44<br>
 */
public class Issue implements Serializable {

    private static final long serialVersionUID = 8068995269823104033L;
    private String ISSUE_ID;//表ID
    private String CLASS_NAME;//课程名
    private String TYPE_ID;//类型id
    private String CLASS_DEATIL;//发布课程内容
    private String ISSUE_TIME;//发布课程时间
    private String EVALUATE_NBR;//评价nbr
    private String ISSUE_STATUS;//发布状态
    private Date CREAT_TIME;//创建时间
    private Date UPDATE_TIME;//更新时间

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getISSUE_ID() {
        return ISSUE_ID;
    }

    public void setISSUE_ID(String ISSUE_ID) {
        this.ISSUE_ID = ISSUE_ID;
    }

    public String getCLASS_NAME() {
        return CLASS_NAME;
    }

    public void setCLASS_NAME(String CLASS_NAME) {
        this.CLASS_NAME = CLASS_NAME;
    }

    public String getTYPE_ID() {
        return TYPE_ID;
    }

    public void setTYPE_ID(String TYPE_ID) {
        this.TYPE_ID = TYPE_ID;
    }

    public String getCLASS_DEATIL() {
        return CLASS_DEATIL;
    }

    public void setCLASS_DEATIL(String CLASS_DEATIL) {
        this.CLASS_DEATIL = CLASS_DEATIL;
    }

    public String getISSUE_TIME() {
        return ISSUE_TIME;
    }

    public void setISSUE_TIME(String ISSUE_TIME) {
        this.ISSUE_TIME = ISSUE_TIME;
    }

    public String getEVALUATE_NBR() {
        return EVALUATE_NBR;
    }

    public void setEVALUATE_NBR(String EVALUATE_NBR) {
        this.EVALUATE_NBR = EVALUATE_NBR;
    }

    public String getISSUE_STATUS() {
        return ISSUE_STATUS;
    }

    public void setISSUE_STATUS(String ISSUE_STATUS) {
        this.ISSUE_STATUS = ISSUE_STATUS;
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

        Issue issue = (Issue) o;

        if (ISSUE_ID != null ? !ISSUE_ID.equals(issue.ISSUE_ID) : issue.ISSUE_ID != null) return false;
        if (CLASS_NAME != null ? !CLASS_NAME.equals(issue.CLASS_NAME) : issue.CLASS_NAME != null) return false;
        if (TYPE_ID != null ? !TYPE_ID.equals(issue.TYPE_ID) : issue.TYPE_ID != null) return false;
        if (CLASS_DEATIL != null ? !CLASS_DEATIL.equals(issue.CLASS_DEATIL) : issue.CLASS_DEATIL != null) return false;
        if (ISSUE_TIME != null ? !ISSUE_TIME.equals(issue.ISSUE_TIME) : issue.ISSUE_TIME != null) return false;
        if (EVALUATE_NBR != null ? !EVALUATE_NBR.equals(issue.EVALUATE_NBR) : issue.EVALUATE_NBR != null) return false;
        if (ISSUE_STATUS != null ? !ISSUE_STATUS.equals(issue.ISSUE_STATUS) : issue.ISSUE_STATUS != null) return false;
        if (CREAT_TIME != null ? !CREAT_TIME.equals(issue.CREAT_TIME) : issue.CREAT_TIME != null) return false;
        return UPDATE_TIME != null ? UPDATE_TIME.equals(issue.UPDATE_TIME) : issue.UPDATE_TIME == null;
    }

    @Override
    public int hashCode() {
        int result = ISSUE_ID != null ? ISSUE_ID.hashCode() : 0;
        result = 31 * result + (CLASS_NAME != null ? CLASS_NAME.hashCode() : 0);
        result = 31 * result + (TYPE_ID != null ? TYPE_ID.hashCode() : 0);
        result = 31 * result + (CLASS_DEATIL != null ? CLASS_DEATIL.hashCode() : 0);
        result = 31 * result + (ISSUE_TIME != null ? ISSUE_TIME.hashCode() : 0);
        result = 31 * result + (EVALUATE_NBR != null ? EVALUATE_NBR.hashCode() : 0);
        result = 31 * result + (ISSUE_STATUS != null ? ISSUE_STATUS.hashCode() : 0);
        result = 31 * result + (CREAT_TIME != null ? CREAT_TIME.hashCode() : 0);
        result = 31 * result + (UPDATE_TIME != null ? UPDATE_TIME.hashCode() : 0);
        return result;
    }
}
