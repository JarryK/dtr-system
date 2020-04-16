package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bmo.AdminBmo;
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
import java.util.HashMap;
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
}
