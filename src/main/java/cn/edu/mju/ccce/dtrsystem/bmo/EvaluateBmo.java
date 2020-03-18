package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.EvaluateStu;

import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bmo.EvaluateBmo<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-03-16 10:14<br>
 */
public interface EvaluateBmo {

    /**
     * 创建一条新的评价记录
     * @param e
     * @return
     */
    public Map<String,Object> createEvaluateStu(EvaluateStu e);

    /**
     * 查找评价记录
     * @param courseID
     * @param userNbr
     * @return map key=EvaluateStu
     */
    public Map<String,Object> getEvaluateStu(String courseID, String userNbr);
}
