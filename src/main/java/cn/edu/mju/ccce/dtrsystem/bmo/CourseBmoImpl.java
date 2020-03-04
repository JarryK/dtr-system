package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bean.Reservation;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.IdGenerator;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import cn.edu.mju.ccce.dtrsystem.dao.CourseDao;
import cn.edu.mju.ccce.dtrsystem.dao.CourseTypeDao;
import cn.edu.mju.ccce.dtrsystem.dao.LoginDao;
import cn.edu.mju.ccce.dtrsystem.dao.ReservationDao;
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

    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.LoginDao")
    protected LoginDao loginDao;

    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.ReservationDao")
    protected ReservationDao reseDao;

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
            log.error("查询可预约课程列表异常:",e);
            return G.bmo.returnMap(false, "查询异常！");
        }
    }

    @Override
    public Map<String, Object> getCourseDetByID(String courseID) {
        try{
            Course course = courseDao.selectCourseByID(courseID);
            String courseDet = course.getCOURSE_DETAIL();
            if (courseDet.isEmpty()){
                return G.bmo.returnMap(false,"查询为空！");
            }
            Map<String,Object> returnMap = G.bmo.returnMap(true,"ok");
            returnMap.put("courseDet",course);
            return returnMap;
        }catch (Exception e){
            log.error("查询课程详细异常:",e);
            return G.bmo.returnMap(false, "查询异常！");
        }

    }

    @Override
    public  Map<String, Object> reservationCourse(Map<String, Object> inMap) {
        synchronized(this) {
            try{
                String courseID = MapTool.getString(inMap,"courseID");
                Course course =courseDao.selectCourseByID(courseID);;
                int stuNbr = course.getCOURSE_STU_NBR();
                int doneStuNbr = course.getCOURSE_DONE_STU_NBR();
                if (stuNbr == doneStuNbr){
                    return G.bmo.returnMap(false,"预约课程人数已满");
                }
                Date nowTime  = new Date();
                Date courseTime = course.getCOURSE_TIME();
                long t = nowTime.getTime() - courseTime.getTime();
                if (t  >  -60*60*1000 ){
                    return G.bmo.returnMap(false,"不能预约即将开课的课程");
                };
                Reservation reservation = new Reservation();
                reservation.setRESERVATION_ID(IdGenerator.genLongId());
                reservation.setCOURSE_ID(Integer.parseInt(courseID));
                reservation.setCOURSE_TEACHER_NBR(course.getCOURSE_TEACHER_NBR());
                reservation.setCOURSE_TEACHER_NAME(course.getCOURSE_TEACHER_NAME());
                reservation.setUSER_NAME(MapTool.getString(inMap,"userName"));
                reservation.setUSER_NBR((Integer) MapTool.getObject(inMap,"userNbr"));
                reservation.setCREAT_TIME(nowTime);
                reseDao.insertReservationRecord(reservation);
                return G.bmo.returnMap(true,"预约成功！");
            }catch (Exception e){
                log.error("预约课程异常",e);
                return G.bmo.returnMap(false,"预约课程异常！");
            }
           }
    }

    @Override
    public Map<String, Object> removeCourse(Map<String, Object> inMap) {
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

}
