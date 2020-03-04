package cn.edu.mju.ccce.dtrsystem.dao;

import cn.edu.mju.ccce.dtrsystem.bean.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.dao.ReservationDao<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-03-04 14:15<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.dao.ReservationDao")
public interface ReservationDao {

    /**
     * 增加一条新的预约课程信息
     * @param reservation
     */
    public void insertReservationRecord(Reservation reservation);

    /**
     * 根据用户编号查询用户预约记录
     * @param userNbr
     * @return
     */
    public List<Reservation> selectAllReservationRecordByUserNbr(String userNbr);

    /**
     * 根据课程ID查询所有预约的记录
     * @param courseID
     * @return
     */
    public List<Reservation> selectAllReservationRecordByCourseID(String courseID);


    /**
     * 根据老师工号查找所有发布课程的预约记录的courseID
     * @param teacherNbr
     * @return
     */
    public List<String> selectAllReservationRecordByTeacherNbr(String teacherNbr);

}
