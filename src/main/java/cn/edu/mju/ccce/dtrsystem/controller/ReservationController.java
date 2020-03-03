package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bmo.CourseBmo;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.ReservationController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>课程预约控制<br>
 * <b>创建时间：</b>2020-02-25 22:15<br>
 */
@Controller
@RequestMapping("/dtr/rese")
public class ReservationController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
    private CourseBmo courseBmo;

    /**
     * 获取可预约课程列表
     *
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public Map<String, Object> getCourseList() {
        try {
            Map<String, Object> returnMap = new HashMap<>();
            Map<String, Object> courseListMap = courseBmo.getCanReservationCourseList();
            boolean listMapBoolean = G.bmo.returnMapBool(courseListMap);
            if (!listMapBoolean) {
                String msg = G.bmo.returnMapMsg(courseListMap);
                returnMap = G.page.returnMap(false, msg);
                return returnMap;
            }
            List<Course> courseList = (List<Course>) MapTool.getObject(courseListMap, "courseList");
            //不做查询是否为空检查  bmo已经做了检查返回
            // 阿里fastjson返回首字母总是小写,暂时没有更好的解决办法  暂时这样
            JSONArray courseListFormat = JSONArray.parseArray(toJSONString(courseList, new PascalNameFilter()));
            returnMap = G.page.returnMap(true, "ok");
            returnMap.put("courseList", courseListFormat);
            return returnMap;
        } catch (Exception e) {
            log.error("获取可预约课程列表异常：", e);
            return G.page.returnMap(false, "获取可预约课程列表异常");
        }
    }

    /**
     * 预约课程
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/reseCourse")
    @ResponseBody
    public Map<String, Object> reserCourse(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String courseID = MapTool.getString(inMap, "index");
        try {
            courseID.substring(1);// 探测非空
        } catch (Exception e) {
            return G.page.returnMap(false, "查询条件异常");
        }
        return null;
    }

    /**
     * 查询课程详细
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/getCourseDet")
    @ResponseBody
    public Map<String, Object> getCourseDet(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String courseID = MapTool.getString(inMap, "index");
        try {
            courseID.substring(1);// 探测非空
        } catch (Exception e) {
            return G.page.returnMap(false, "查询条件异常");
        }
        try {
            Map<String, Object> relMap = courseBmo.getCourseDetByID(courseID);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            Course courseDet = (Course) MapTool.getObject(relMap, "courseDet");
            //不做查询是否为空检查  bmo已经做了检查返回
            // 阿里fastjson返回首字母总是小写,暂时没有更好的解决办法  暂时这样
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("courseDet", courseDet.getCOURSE_DETAIL());
            return returnMap;
        } catch (Exception e) {
            log.error("查询课程详细异常：", e);
            return G.page.returnMap(false, "查询课程详细异常");
        }
    }

}
