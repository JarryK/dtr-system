package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bmo.CourseBmo;
import cn.edu.mju.ccce.dtrsystem.common.G;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.EvaluateController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>课程预约控制<br>
 * <b>创建时间：</b>2020-02-11 20:17<br>
 */
@Controller
@RequestMapping("/dtr/eval")
public class EvaluateController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
    private CourseBmo courseBmo;

    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(@RequestBody Map<String, Object> inMap) {
        try {
            String courseName = G.getString(inMap, "courseName");
            String typeId = G.getString(inMap, "typeId");
            String courseDetail = G.getString(inMap, "courseDetail");
//            String courseStuNbr = G.getString(inMap, "courseStuNbr");
//            String couresTime = G.getString(inMap, "couresTime");
            Course course = new Course();
            course.setCOURSE_NAME(courseName);
            course.setTYPE_ID(Integer.parseInt(typeId));
            course.setCOURSE_DETAIL(courseDetail);
//            course.setCOURSE_STU_NBR(Integer.parseInt(courseStuNbr));
//            course.setCOURSE_TIME(couresTime);
            courseBmo.addCourse(course);
            return G.page.returnMap(true,"ok");
        } catch (Exception e) {
            log.error("新建异常：",e);
            return G.page.returnMap(false,"no");

        }

    }
}
