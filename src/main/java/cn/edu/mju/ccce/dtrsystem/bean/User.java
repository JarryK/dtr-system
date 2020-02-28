package cn.edu.mju.ccce.dtrsystem.bean;

import java.io.Serializable;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bean.User<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-08 20:35<br>
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1320367561013449636L;
    private int USER_ID;
    private String USER_NAME;
    private int USER_NBR;
    private String USER_SEX;
    private int TYPE_ID;
    private int USER_PHONE;
    private String USER_PASS;
    private int EVALUATE_NBR;
    private int USER_STATUS;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public int getUSER_NBR() {
        return USER_NBR;
    }

    public void setUSER_NBR(int USER_NBR) {
        this.USER_NBR = USER_NBR;
    }

    public String getUSER_SEX() {
        return USER_SEX;
    }

    public void setUSER_SEX(String USER_SEX) {
        this.USER_SEX = USER_SEX;
    }

    public int getTYPE_ID() {
        return TYPE_ID;
    }

    public void setTYPE_ID(int TYPE_ID) {
        this.TYPE_ID = TYPE_ID;
    }

    public int getUSER_PHONE() {
        return USER_PHONE;
    }

    public void setUSER_PHONE(int USER_PHONE) {
        this.USER_PHONE = USER_PHONE;
    }

    public String getUSER_PASS() {
        return USER_PASS;
    }

    public void setUSER_PASS(String USER_PASS) {
        this.USER_PASS = USER_PASS;
    }

    public int getEVALUATE_NBR() {
        return EVALUATE_NBR;
    }

    public void setEVALUATE_NBR(int EVALUATE_NBR) {
        this.EVALUATE_NBR = EVALUATE_NBR;
    }

    public int getUSER_STATUS() {
        return USER_STATUS;
    }

    public void setUSER_STATUS(int USER_STATUS) {
        this.USER_STATUS = USER_STATUS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (USER_ID != user.USER_ID) return false;
        if (USER_NBR != user.USER_NBR) return false;
        if (TYPE_ID != user.TYPE_ID) return false;
        if (USER_PHONE != user.USER_PHONE) return false;
        if (EVALUATE_NBR != user.EVALUATE_NBR) return false;
        if (USER_STATUS != user.USER_STATUS) return false;
        if (USER_NAME != null ? !USER_NAME.equals(user.USER_NAME) : user.USER_NAME != null) return false;
        if (USER_SEX != null ? !USER_SEX.equals(user.USER_SEX) : user.USER_SEX != null) return false;
        return USER_PASS != null ? USER_PASS.equals(user.USER_PASS) : user.USER_PASS == null;
    }

    @Override
    public int hashCode() {
        int result = USER_ID;
        result = 31 * result + (USER_NAME != null ? USER_NAME.hashCode() : 0);
        result = 31 * result + USER_NBR;
        result = 31 * result + (USER_SEX != null ? USER_SEX.hashCode() : 0);
        result = 31 * result + TYPE_ID;
        result = 31 * result + USER_PHONE;
        result = 31 * result + (USER_PASS != null ? USER_PASS.hashCode() : 0);
        result = 31 * result + EVALUATE_NBR;
        result = 31 * result + USER_STATUS;
        return result;
    }
}
