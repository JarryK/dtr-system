package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bmo.CourseBmo;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.EvaluateController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>课程发布控制<br>
 * <b>创建时间：</b>2020-02-11 20:17<br>
 */
@Controller
@RequestMapping("/dtr/issue")
public class IssueController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
    private CourseBmo courseBmo;


    @RequestMapping("getType")
    @ResponseBody
    public Map<String, Object> getType(HttpSession httpSession) {
        String sessionID = "";
        try {
            sessionID = httpSession.getId();
        } catch (Exception e) {
            return G.page.returnMap(false, "请先登录");
        }
        try {
            Map<String, Object> typeMap = courseBmo.getAllCourseType();
            boolean relMapBoolean = G.bmo.returnMapBool(typeMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(typeMap);
                return G.page.returnMap(false, msg);
            }
            List<Map<String, Object>> typeList = (List<Map<String, Object>>) MapTool.getObject(typeMap, "typeList");
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("typeList", typeList);
            return returnMap;
        } catch (Exception e) {
            log.error("课程类别异常：", e);
            return G.page.returnMap(false, "课程类别异常");
        }
    }

    /**
     * 新建可以预约的课程
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String sessionID = "";
        try {
            sessionID = httpSession.getId();
        } catch (Exception e) {
            return G.page.returnMap(false, "请先登录");
        }
        String courseName = MapTool.getString(inMap, "courseName");
        String typeName = MapTool.getString(inMap, "typeName");
        String courseDetail = MapTool.getString(inMap, "courseDetail");
        String courseStuNbr = MapTool.getString(inMap, "courseStuNbr");
        String courseTimeStr = MapTool.getString(inMap, "courseTime");
        try {
            courseName.substring(1);//探测非空
            typeName.substring(1);
            courseDetail.substring(1);
            courseStuNbr.substring(1);
            courseTimeStr.substring(1);
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空！");
        }
        try {
            int typeId = courseBmo.getCourseIDbyName(typeName);
            Date courseTime = formatTimeStrToDate(courseTimeStr);
            Map<String, Object> uMgs = (Map<String, Object>) httpSession.getAttribute(sessionID);
            //session里面最好改成用户类,不过比较懒  先放着
            String uName = MapTool.getString(uMgs, "USER_NAME");
            String userNbr = MapTool.getString(uMgs, "USER_NBR");
            Course course = new Course();
            course.setCOURSE_NAME(courseName);
            course.setCOURSE_TYPE_ID(typeId);
            course.setCOURSE_TYPE_NAME(typeName);
            course.setCOURSE_DETAIL(courseDetail);
            course.setCOURSE_STU_NBR(Integer.parseInt(courseStuNbr));
            course.setCOURSE_TIME(courseTime);
            course.setCOURSE_TEACHER_NAME(uName);
            course.setCOURSE_STATUS(0);
            course.setCOURSE_TEACHER_NBR(Integer.parseInt(userNbr));
            Map<String, Object> relMap = courseBmo.addCourse(course);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            return G.page.returnMap(true, "ok");
        } catch (Exception e) {
            log.error("新建异常：", e);
            return G.page.returnMap(false, "新建异常");
        }

    }

    private Date formatTimeStrToDate(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date courseTime = null;
        try {
            courseTime = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return courseTime;
    }

    /**
     * 获取所有已发布的课程
     *
     * @param httpSession
     * @return
     */
    @RequestMapping("/getSelfIssue")
    @ResponseBody
    public Map<String, Object> getSelfIssue(HttpSession httpSession) {
        String sessionID = "";
        try {
            sessionID = httpSession.getId();
        } catch (Exception e) {
            return G.page.returnMap(false, "请先登录");
        }
        try {
            Map<String, Object> uMsg = (Map<String, Object>) httpSession.getAttribute(sessionID);
            String type_name = MapTool.getString(uMsg, "TYPE_NAME");
            if (!"教师".equals(type_name)) {
                return G.page.returnMap(false, "非教师用户，不能操作");
            }
            String uNbr = MapTool.getString(uMsg, "USER_NBR");
            Map<String, Object> Map = new HashMap<>();
            // 做提前声明，不然前端数据异常也不会报错自己还偷偷吃掉这个BUG
            Map.put("noIssueList", "");
            Map.put("noIssueDoneList", "");
            Map<String, Object> relMap = courseBmo.getCourseListByTeacherNbr(uNbr);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            List<Course> courseList = (List<Course>) MapTool.getObject(relMap, "courseList");
            if (courseList.isEmpty()) {
                Map.put("noIssueList", "暂无记录");
            }
            Map<String, Object> relDoneMap = courseBmo.getCourseDoneListByTeacherNbr(uNbr);
            boolean relDoneMapBoolean = G.bmo.returnMapBool(relDoneMap);
            if (!relDoneMapBoolean) {
                String msg = G.bmo.returnMapMsg(relDoneMap);
                return G.page.returnMap(false, msg);
            }
            List<Course> courseDoneList = (List<Course>) MapTool.getObject(relDoneMap, "courseDoneList");
            if (courseDoneList.isEmpty()) {
                Map.put("noIssueDoneList", "暂无记录");
            }
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.putAll(Map);
            returnMap.put("courseList", courseList);
            returnMap.put("courseDoneList", courseDoneList);
            return returnMap;
        } catch (Exception e) {
            log.error("查询异常：", e);
            return G.page.returnMap(false, "查询异常");
        }
    }

    /**
     * 获得已预约本课程的学生名单
     *
     * @param inMap
     * @return
     */
    @RequestMapping("/getCourseStu")
    @ResponseBody
    public Map<String, Object> getCourseStu(@RequestBody Map<String, Object> inMap) {
        String courseID = MapTool.getString(inMap, "courseID");
        try {
            courseID.substring(1);//探测非空;
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空");
        }
        try {
            Map<String, Object> relMap = courseBmo.getCourseStuList(courseID);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            List<Map<String, Object>> userList = (List<Map<String, Object>>) MapTool.getObject(relMap, "userList");
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("userList", userList);
            return returnMap;
        } catch (Exception e) {
            log.error("查询异常：", e);
            return G.page.returnMap(false, "查询异常");
        }
    }

    /**
     * 获得已完成课程的学生名单
     *
     * @param inMap
     * @return
     */
    @RequestMapping("/getDoneCourseStu")
    @ResponseBody
    public Map<String, Object> getDoneCourseStu(@RequestBody Map<String, Object> inMap) {
        String courseID = MapTool.getString(inMap, "courseID");
        try {
            courseID.substring(1);//探测非空;
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空");
        }
        try {
            Map<String, Object> relMap = courseBmo.getDoneCourseStuList(courseID);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            List<Map<String, Object>> userList = (List<Map<String, Object>>) MapTool.getObject(relMap, "userList");
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("userList", userList);
            return returnMap;
        } catch (Exception e) {
            log.error("查询异常：", e);
            return G.page.returnMap(false, "查询异常");
        }
    }

    /**
     * 更新课程信息
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/updateCourse")
    @ResponseBody
    public Map<String, Object> updateCourse(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String sessionID = "";
        try {
            sessionID = httpSession.getId();
        } catch (Exception e) {
            return G.page.returnMap(false, "请先登录");
        }
        String courseID = MapTool.getString(inMap, "courseID");
        String courseName = MapTool.getString(inMap, "courseName");
        String courseDetail = MapTool.getString(inMap, "courseDetail");
        String courseTimeStr = MapTool.getString(inMap, "courseTime");
        try {
            courseID.substring(1);//探测非空;
            courseName.substring(1);
            courseTimeStr.substring(1);
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空");
        }
        try {
            //给定输出格式
            Date courseTime = formatTimeStrToDate(courseTimeStr);
            Map<String, Object> uMap = (Map<String, Object>) httpSession.getAttribute(sessionID);
            String type_name = MapTool.getString(uMap, "TYPE_NAME");
            if (!"教师".equals(type_name)) {
                return G.page.returnMap(false, "非教师用户，不能操作");
            }
            Map<String, Object> relMap = courseBmo.getCourseDetByID(courseID);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            Course course = (Course) MapTool.getObject(relMap, "courseDet");
            course.setCOURSE_NAME(courseName);
            course.setCOURSE_DETAIL(courseDetail);
            course.setCOURSE_TIME(courseTime);
            Map<String, Object> updateMap = courseBmo.upDateCourse(course);
            boolean updateMapBoolean = G.bmo.returnMapBool(updateMap);
            if (!updateMapBoolean) {
                String msg = G.bmo.returnMapMsg(updateMap);
                return G.page.returnMap(false, msg);
            }
            return G.page.returnMap(true, "ok");

        } catch (Exception e) {
            log.error("更新课程异常：", e);
            return G.page.returnMap(false, "更新课程异常");
        }
    }

    /**
     * 取消以发布的课程
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/cancelCourse")
    @ResponseBody
    public Map<String, Object> cancelCourse(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String sessionID = "";
        try {
            sessionID = httpSession.getId();
        } catch (Exception e) {
            return G.page.returnMap(false, "请先登录");
        }
        String courseID = MapTool.getString(inMap, "courseID");
        try {
            courseID.substring(1);//探测非空;
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空");
        }
        try {
            Map<String, Object> uMap = (Map<String, Object>) httpSession.getAttribute(sessionID);
            String type_name = MapTool.getString(uMap, "TYPE_NAME");
            if (!"教师".equals(type_name)) {
                return G.page.returnMap(false, "非教师用户，不能操作");
            }
            Map<String, Object> relMap = courseBmo.cancelCourse(courseID);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.page.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            return G.page.returnMap(true, "ok");
        } catch (Exception e) {
            log.error("取消课程异常：", e);
            return G.page.returnMap(false, "取消课程异常");
        }
    }

    @RequestMapping("getToday")
    @ResponseBody
    public Map<String, Object> getToday(HttpSession httpSession) {
        try {
            Map<String, Object> relMap = courseBmo.getTodayCourse();
            List<Course> courseList = (List<Course>) MapTool.getObject(relMap, "courseList");
            if (courseList == null) {
                return G.page.returnMap(false, "查找为空");
            }
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.page.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("courseList", courseList);
            return returnMap;
        } catch (Exception e) {
            log.error("查找今天课程异常：", e);
            return G.page.returnMap(false, "查找今天课程异常");
        }
    }

    @RequestMapping("getHistory")
    @ResponseBody
    public Map<String, Object> getHistory(HttpSession httpSession) {
        try {
            Map<String, Object> relMap = courseBmo.getHistory();
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.page.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            List<Course> pastWeek = (List<Course>) MapTool.getObject(relMap, "pastWeek");
            List<Map<String, Object>> pastMonth = (List<Map<String, Object>>) MapTool.getObject(relMap, "pastMonth");
            List<Map<String, Object>> weekList = parseWeekCourseList(pastWeek);
            Map<String, Object> mapList = parseMonthCourseList(pastMonth);
            if (weekList.isEmpty() || mapList.isEmpty()) {
                return G.page.returnMap(false, "查找历史课程为空");
            }
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("pastWeek", weekList);
            returnMap.put("pastMonth", mapList);
            returnMap.put("typeList", getCourseTypeList());
            return returnMap;
        } catch (Exception e) {
            log.error("查找历史课程异常：", e);
            return G.page.returnMap(false, "查找历史课程异常");
        }
    }

    private List<Map<String, Object>> parseWeekCourseList(List<Course> courseList) throws Exception {
        List<String> typeList = getCourseTypeList();
        if (typeList.isEmpty()) {
            return new ArrayList<>();
        }
        Map<String, Object> returnMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String s : typeList) {
            List<Course> sl = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            for (Course c : courseList) {
                String sc = c.getCOURSE_TYPE_NAME();
                if (s.equals(sc)) {
                    sl.add(c);
                }
            }
            map.put("value", sl.size());
            map.put("courseList", sl);
            map.put("name", s);
            map.put("tID", courseBmo.getCourseIDbyName(s));
            mapList.add(map);
        }
        return mapList;
    }

    private Map<String, Object> parseMonthCourseList(List<Map<String, Object>> courseList) throws Exception {
        List<String> typeList = getCourseTypeList();
        if (typeList.isEmpty()) {
            return new HashMap<>();
        }
        List<Map<String, Object>> sl2 = new ArrayList<>();
        Map<String, Object> returnMap = new HashMap<>();
        for (String s : typeList) {
            List<Integer> sl = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String st = "";
            String lastName = "";
            int k = 0;
            for (Map<String, Object> m : courseList) {
                String mtime = MapTool.getString(m, "time");
                String cs = MapTool.getString(m, "name");
                if ("".equals(cs) || cs == null) {
                    sl.add(0);
                    k++;
                } else {
                    if (st.equals(mtime)) {
                        if (s.equals(cs)) {
                            sl.set((k - 1), sl.get(k - 1) + 1);
                        }
                    } else {
                        if (s.equals(cs)) {
                            sl.add(1);
                        } else {
                            sl.add(0);
                        }
                        k++;
                    }
                }
                lastName = cs;
                st = mtime;
            }
            map.put("name", s);
            map.put("type", "line");
            map.put("data", sl);
            sl2.add(map);
        }
        returnMap.put("courseList", sl2);
        return returnMap;
    }

    private List<String> getCourseTypeList() throws Exception {
        Map<String, Object> typeMap = courseBmo.getAllCourseType();
        boolean relMapBoolean = G.bmo.returnMapBool(typeMap);
        if (!relMapBoolean) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> typeMapList = (List<Map<String, Object>>) MapTool.getObject(typeMap, "typeList");
        List<String> typeList = new ArrayList<>();
        for (Map<String, Object> m : typeMapList) {
            String type = MapTool.getString(m, "COURSE_TYPE_NAME");
            typeList.add(type);
        }
        return typeList;
    }

    private List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
}
