package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.IdGenerator;
import cn.edu.mju.ccce.dtrsystem.dao.CourseDao;
import cn.edu.mju.ccce.dtrsystem.dao.CourseTypeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-11 19:49<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
public class CourseBmoImpl implements CourseBmo {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.CourseDao")
    protected CourseDao courseDao;

    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.CourseTypeDao")
    protected CourseTypeDao courseTypeDao;

    @Override
    public Map<String, Object> addCourse(Course course) {
        try {
            course.setCOURSE_ID(BigInteger.valueOf(IdGenerator.genLongId()));
            course.setEVALUATE_NBR(BigInteger.valueOf(IdGenerator.genLongId()));
            course.setCREAT_TIME(new Date());
            courseDao.insertNewCourse(course);
            return G.bmo.returnMap(true, "ok");
        } catch (Exception e) {
            log.error("新建课程异常：",e);
            return G.bmo.returnMap(false, "创建失败！");
        }
    }

    @Override
    public Map<String, Object> removeCourse(Map<String, Object> inMap) {
        return null;
    }

    @Override
    public Map<String, Object> reservationCourse(Map<String, Object> inMap) {
        return null;
    }

    @Override
    public Map<String, Object> upDateCourse(Map<String, Object> inMap) {
        return null;
    }

    public Map<String, Object> addEvaluateCourse(Map<String, Object> inMap) {
        return null;
    }

    public Map<String, Object> removeEvaluateCourse(Map<String, Object> inMap) {
        return null;
    }

    @Override
    public int getCourseIDbyName(String courseTypeName) {
        return Integer.parseInt(courseTypeDao.selectCourseTypeId(courseTypeName));
    }

    @Override
    public Map<String,Object> getCanReservationCourseList() {
        try{
            List<Course> courseList= courseDao.selectCourseList();
            if (courseList.isEmpty()){
                return G.bmo.returnMap(false,"查询为空！");
            }
            Map<String,Object> returnMap = G.bmo.returnMap(true,"ok");
            returnMap.put("courseList",courseList);
            return returnMap;
        }catch (Exception e){

        }
        return null;
    }
}
