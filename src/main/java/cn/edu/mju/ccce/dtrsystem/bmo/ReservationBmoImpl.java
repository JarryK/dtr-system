package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bean.Reservation;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.IdGenerator;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import cn.edu.mju.ccce.dtrsystem.dao.CourseDao;
import cn.edu.mju.ccce.dtrsystem.dao.ReservationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bmo.ReservationBmoImpl<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-03-05 10:18<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.bmo.ReservationBmoImpl")
public class ReservationBmoImpl implements ReservationBmo {
    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.CourseDao")
    protected CourseDao courseDao;

    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.ReservationDao")
    protected ReservationDao reseDao;

    /**
     * 预约课程
     *
     * @param inMap
     * @return map
     */
    @Override
    public Map<String, Object> reservationCourse(Map<String, Object> inMap) {
        synchronized (this) {
            try {
                String courseID = MapTool.getString(inMap, "courseID");
                Course course = courseDao.selectCourseByID(courseID);
                int stuNbr = course.getCOURSE_STU_NBR();
                int doneStuNbr = course.getCOURSE_DONE_STU_NBR();
                if (stuNbr == doneStuNbr || stuNbr < doneStuNbr) {
                    return G.bmo.returnMap(false, "预约课程人数已满");
                }
                Date nowTime = new Date();
                Date courseTime = course.getCOURSE_TIME();
                long t = nowTime.getTime() - courseTime.getTime();
                // 开课前两小时不能预约
                if (t > -2 * 60 * 60 * 1000) {
                    return G.bmo.returnMap(false, "不能预约即将开课的课程");
                }
                Reservation reservation = new Reservation();
                reservation.setRESERVATION_ID(IdGenerator.genLongId());
                reservation.setCOURSE_ID(Long.parseLong(courseID));
                reservation.setCOURSE_TIME(course.getCOURSE_TIME());
                reservation.setCOURSE_TEACHER_NBR(course.getCOURSE_TEACHER_NBR());
                reservation.setCOURSE_TEACHER_NAME(course.getCOURSE_TEACHER_NAME());
                reservation.setCOURSE_TYPE_NAME(course.getCOURSE_TYPE_NAME());
                reservation.setCOURSE_NAME(course.getCOURSE_NAME());
                reservation.setUSER_NAME(MapTool.getString(inMap, "userName"));
                reservation.setUSER_NBR(Long.parseLong(MapTool.getString(inMap, "userNbr")));
                reservation.setCREAT_TIME(nowTime);
                reseDao.insertReservationRecord(reservation);
                courseDao.upDateCourseDoneStuNbr(String.valueOf(doneStuNbr + 1), courseID);
                return G.bmo.returnMap(true, "预约成功！");
            } catch (Exception e) {
                log.error("预约课程异常", e);
                return G.bmo.returnMap(false, "预约课程异常！");
            }
        }
    }

    /**
     * 获取可预约课程列表
     *
     * @param
     * @return map key=courseList
     */
    @Override
    public Map<String, Object> getCanReservationCourseList() {
        try {
            List<Course> courseList = courseDao.selectCourseList();
            if (courseList.isEmpty()) {
                return G.bmo.returnMap(false, "查询为空！");
            }
            Map<String, Object> returnMap = G.bmo.returnMap(true, "ok");
            returnMap.put("courseList", courseList);
            return returnMap;
        } catch (Exception e) {
            log.error("查询可预约课程列表异常:", e);
            return G.bmo.returnMap(false, "查询异常");
        }
    }

    /**
     * 获取用户所有预约未上课的历史记录
     *
     * @param userNbr
     * @return map key=reservationList
     */
    @Override
    public Map<String, Object> getAllReservationCourseByUserNbr(String userNbr) {
        try {
            List<Reservation> reservationList = reseDao.selectAllReservationRecordByUserNbr(userNbr);
            if (reservationList.isEmpty()) {
                G.bmo.returnMap(false, "获取用户预约信息为空");
            }
            Map<String, Object> returnMap = G.bmo.returnMap(true, "ok");
            returnMap.put("reservationList", reservationList);
            return returnMap;
        } catch (Exception e) {
            log.error("获取用户预约信息异常：", e);
            return G.bmo.returnMap(false, "获取用户预约信息异常");
        }
    }

    ;
}
