package cn.edu.mju.ccce.dtrsystem.dao;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import org.springframework.stereotype.Service;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.dao.CourseTypeDao<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-18 20:42<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.dao.CourseTypeDao")
public interface CourseTypeDao {

    String selectCourseTypeId(String courseTypeName);
}
