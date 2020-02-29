package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bmo.CourseBmo;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.ReservationController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>课程预约控制<br>
 * <b>创建时间：</b>2020-02-25 22:15<br>
 */
@Controller
@RequestMapping("/dtr/reser")
public class ReservationController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
    private CourseBmo courseBmo;

    @RequestMapping("/getList")
    @ResponseBody
    public Map<String, Object> getCourseList(@RequestBody Map<String, Object> inMap) {
        try {
            Map<String, Object> returnMap = new HashMap<>();
            Map<String, Object> courseListMap = courseBmo.getCanReservationCourseList();
            boolean listMapBoolean = G.bmo.returnMapBool(courseListMap);
            String msg = G.bmo.returnMapMsg(courseListMap);
            if (!listMapBoolean) {
                returnMap = G.page.returnMap(false, msg);
                return returnMap;
            }
            List<Course> courseList = (List<Course>) MapTool.getObject(courseListMap, "courseList");
            if (courseList.isEmpty()) {
                returnMap = G.page.returnMap(false, "可预约列表为空");
                return returnMap;
            }
            JSONArray courseListFormat = JSONArray.parseArray(JSON.toJSONString(courseList,new PascalNameFilter()));
            returnMap = G.page.returnMap(true, "ok");
            returnMap.put("courseList",courseListFormat );
            return returnMap;
        } catch (Exception e) {
            log.error("获取可预约课程列表异常：",e);
            return G.page.returnMap(false,"获取可预约课程列表异常");

        }
    }

}
