package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bmo.AdminBmo;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.AdminController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-04-15 12:49<br>
 */
@Controller
@RequestMapping("dtr/admin")
public class AdminController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.AdminBmoImpl")
    private AdminBmo adminBmo;

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
    private CourseBmo courseBmo;

    @RequestMapping("/admin-login")
    @ResponseBody
    public Map<String, Object> adminLogin(@RequestBody Map<String, Object> inMap, HttpSession session) {
        if (inMap.isEmpty()) {
            return G.page.returnMap(false, "非法输入！");
        }
        try {
            String adminId = MapTool.getString(inMap, "adminID");
            String adminPass = MapTool.getString(inMap, "adminPass");
            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("adminId", adminId);
            adminMap.put("adminPass", adminPass);
            Map<String, Object> relMap = adminBmo.checkAdminLogin(adminMap);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            Map<String, Object> userMsgMap = MapTool.getMap(relMap, "admin");
            if (!relMapBoolean) {
                return G.page.returnMap(false, "用户名或密码错误！");
            }
            session.setAttribute(session.getId(), userMsgMap);
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.putAll(userMsgMap);
            return returnMap;
        } catch (Exception e) {
            log.error("管理员登录异常：", e);
            return G.page.returnMap(false, "管理员登录异常");
        }
    }

    @RequestMapping("/getAdmin")
    @ResponseBody
    public Map<String, Object> getAdmin(HttpSession session) {
        try {
            String sessionID = session.getId();
            Map<String, Object> userMsgMap = (Map<String, Object>) session.getAttribute(sessionID);
            if (userMsgMap.isEmpty()) {
                return G.page.returnMap(false, "请先登录！");
            }
            String id = MapTool.getString(userMsgMap, "id");
            String sex = MapTool.getString(userMsgMap, "sex");
            String name = MapTool.getString(userMsgMap, "name");
            String phone = MapTool.getString(userMsgMap, "phone");
            Map<String, Object> relMap = adminBmo.getAdmin(id);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.page.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            Map<String, Object> admin = MapTool.getMap(relMap, "admin");
            String rel_id = MapTool.getString(admin, "id");
            String rel_name = MapTool.getString(admin, "name");
            String rel_sex = MapTool.getString(admin, "sex");
            String rel_phone = MapTool.getString(admin, "phone");
            if (id.equals(rel_id) && name.equals(rel_name) && phone.equals(rel_phone) && sex.equals(rel_sex)) {
                Map<String, Object> returnMap = G.page.returnMap(true, "ok");
                returnMap.put("admin",admin);
                return returnMap;
            }
            session.removeAttribute(sessionID);
            return G.page.returnMap(false, "信息错误！");
        } catch (Exception e) {
            log.error("获取信息异常：", e);
            return G.page.returnMap(false, "获取信息异常");
        }
    }

    @RequestMapping("/adminOut")
    @ResponseBody
    public Map<String, Object> adminOut(@RequestBody Map<String, Object> inMap, HttpSession session) {
        try {
            String loginOutUserNbr = MapTool.getString(inMap, "adminID");
            String sessionID = session.getId();
            Map<String, Object> userMsgMap = (Map<String, Object>) session.getAttribute(sessionID);
            if (userMsgMap.isEmpty()) {
                return G.page.returnMap(false, "已退出！");
            }
            String userNbr = MapTool.getString(userMsgMap,"id");
            if (!userNbr.equals(loginOutUserNbr)){
                return G.page.returnMap(false,"退出异常");
            }
            session.removeAttribute(sessionID);
            return G.page.returnMap(true, "ok");
        } catch (Exception e) {
            return G.page.returnMap(false, "退出失败");
        }
    }

    @RequestMapping("getHistory")
    @ResponseBody
    public Map<String, Object> getHistory(HttpSession httpSession) {
        try {
            if (!adminBmo.isAdmin(httpSession)){
                return G.page.returnMap(false, "非管理员不可操作！");
            };
            Map<String, Object> relMap = courseBmo.getHistory();
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.page.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            List<Course> pastWeek = (List<Course>) MapTool.getObject(relMap, "pastWeek");
            List<Course> courseList = (List<Course>) MapTool.getObject(relMap,"courseList");
            List<Map<String, Object>> pastMonth = (List<Map<String, Object>>) MapTool.getObject(relMap, "pastMonth");
            List<Map<String, Object>> weekList = parseWeekCourseList(pastWeek);
            Map<String, Object> mapList = parseMonthCourseList(pastMonth);
            if (weekList.isEmpty() && courseList.isEmpty() ) {
                return G.page.returnMap(false, "查找历史课程为空");
            }
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("pastWeek", weekList);
            returnMap.put("pastMonth", mapList);
            returnMap.put("pastMonthCourseList",courseList);
            returnMap.put("typeList", getCourseTypeList());
            return returnMap;
        } catch (Exception e) {
            log.error("查找历史课程异常：", e);
            return G.page.returnMap(false, "查找历史课程异常");
        }
    }

    @RequestMapping("getCouList")
    @ResponseBody
    public Map<String, Object> getCouList(HttpSession httpSession) {
        try {
            if (!adminBmo.isAdmin(httpSession)){
                return G.page.returnMap(false, "非管理员不可操作！");
            };
            Map<String, Object> relMap = courseBmo.getIssueList();
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.page.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            List<Course> oneMonth = (List<Course>) MapTool.getObject(relMap, "oneMonth");
            if (oneMonth.isEmpty() ) {
                return G.page.returnMap(false, "查找课程为空");
            }
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("oneMonth",oneMonth);
            return returnMap;
        } catch (Exception e) {
            log.error("查找课程异常：", e);
            return G.page.returnMap(false, "查找课程异常");
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

}
