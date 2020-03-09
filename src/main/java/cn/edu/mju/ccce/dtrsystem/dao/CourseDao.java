package cn.edu.mju.ccce.dtrsystem.dao;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.dao.CourseDao<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-18 20:42<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.dao.CourseDao")
public interface CourseDao {

    /**
     * 插入一条新的课程信息
     *
     * @param course
     * @return
     */
    public void insertNewCourse(Course course);

    /**
     * 获取可预约课程列表
     *
     * @return
     */
    public List<Course> selectCourseList();

    /**
     * 通过课程ID查找课程信息
     *
     * @param courseID
     * @return
     */
    public Course selectCourseByID(String courseID);

    /**
     * 更新指定课程已预约人数
     *
     * @param courseDoneStuNbr
     * @return
     */
    public void upDateCourseDoneStuNbr(String courseDoneStuNbr,String courseID);

    /**
     * 查找已预约课程人数
     *
     * @return
     */
    public void selectCourseReservationNbr(String CourseId);

    /**
     * 获取老师的课程列表
     * @param courseTeacherNbr
     * @param courseStatus    =0可预约  =1 已过期
     * @return
     */
    public List<Course> selectCourseListByTeacherNbr(String courseTeacherNbr,String courseStatus);


    /**
     * 更新课程信息
     * @param course
     * @return
     */
    public void updateCourse(Course course);
}
