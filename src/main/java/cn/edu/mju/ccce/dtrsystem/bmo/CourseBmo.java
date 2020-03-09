package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.Course;

import java.util.List;
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
     * @param course
     * @return
     */
    public  Map<String, Object> addCourse(Course course);

    /**
     * 获取课程类型id
     * 特殊,不用检测
     * @param courseTypeName
     * @return
     */
    public int getCourseIDbyName(String courseTypeName);

    /**
     * 根据课程ID获得课程全部信息
     * @param courseID
     * @return map key=courseDet
     */
    public Map<String,Object> getCourseDetByID(String courseID);

    /**
     * 根据老师Nbr获取可预约的课程列表
     * @param teacherNbr
     * @return
     */
    public Map<String,Object> getCourseListByTeacherNbr(String teacherNbr);

    /**
     * 根据老师Nbr获取已完成的课程列表
     * @param teacherNbr
     * @return
     */
    public Map<String,Object> getCourseDoneListByTeacherNbr(String teacherNbr);

    /**
     * 删除课程
     * @param inMap
     * @return
     */
    public Map<String,Object> removeCourse (Map<String,Object> inMap);


    /**
     * 更新发布课程信息
     * @param inMap
     * @return
     */
    public Map<String,Object> upDateCourse (Map<String,Object> inMap);
}
