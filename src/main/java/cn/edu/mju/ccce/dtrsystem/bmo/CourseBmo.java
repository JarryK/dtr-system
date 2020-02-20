package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.Course;

import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bmo.CourseBmo<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>课程Bmo<br>
 * <b>创建时间：</b>2020-02-11 19:49<br>
 */
public interface CourseBmo {

    /**
     * 添加课程
     *
     * @param inMap
     * @return
     */
    public  Map<String, Object> addCourse(Course course);

    /**
     * 删除课程
     * @param inMap
     * @return
     */
    public Map<String,Object> removeCourse (Map<String,Object> inMap);

    /**
     *预约课程
     * @param inMap
     * @return
     */
    public Map<String,Object> reservationCourse (Map<String,Object> inMap);

    /**
     * 更新发布课程信息
     * @param inMap
     * @return
     */
    public Map<String,Object> upDateCourse (Map<String,Object> inMap);

    /**
     * 增加课程评价
     * @param inMap
     * @return
     */
    public Map<String,Object> addEvaluateCourse (Map<String,Object> inMap);

    /**
     * 删除课程评价
     * @param inMap
     * @return
     */
    public Map<String,Object> removeEvaluateCourse (Map<String,Object> inMap);
}
