package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bmo.LoginBmoImpl;
import cn.edu.mju.ccce.dtrsystem.common.G;
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
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.LoginContronller<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-02-02 16:49<br>
 */
@Controller
@RequestMapping("/dtr/user")
public class LoginController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.Bmo.LoginBmoImpl")
    private LoginBmoImpl loginBmo;


    @RequestMapping("/check")
    @ResponseBody
    public Map<String, Object> check(@RequestBody Map<String, Object> inMap, HttpSession session) {
        Map<String, Object> returnMap = new HashMap<>();
        String unbr = G.getString(inMap, "uNbr");
        String upass = G.getString(inMap, "uPass");
        String utype = G.getString(inMap, "uType");
        try {
            unbr.substring(1); // 取1个串，探测非空
            upass.substring(1); // 取1个串，探测非空
            if (utype.length() <= 0) {
                utype.substring(1);
            }
            ;
        } catch (Exception e) {
            return G.page.returnMap(false, "输入信息错误！");
        }
        try {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userNbr", unbr);
            userMap.put("userPass", upass);
            userMap.put("userType", utype);
            Map<String, Object> relMap = loginBmo.chackLogin(userMap);
            Boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            Map<String, Object> userMsgMap = G.getMap(relMap, "msg");
            if (!relMapBoolean) {
                return G.page.returnMap(false, "用户名或密码错误！");
            }
            session.setAttribute("user", userMsgMap);
            returnMap = G.page.returnMap(true, "登录成功！");
            returnMap.put("userMsg", userMsgMap);
            return returnMap;
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return G.page.returnMap(false, "登录异常！");
        }
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public Map<String, Object> getUser(HttpSession session) {
        try {
            Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("user");
            if (userMap.isEmpty()) {
                return G.page.returnMap(false, "用户未登录");
            }
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("user", userMap);
            return returnMap;
        } catch (Exception e) {
            return G.page.returnMap(false, "获取用户信息失败");
        }

    }

    @RequestMapping("/loginOut")
    @ResponseBody
    public Map<String, Object> loginOut(@RequestBody Map<String, Object> inMap, HttpSession session) {
        try {
            String userName = G.getString(inMap, "userName");
            session.removeAttribute(userName);
            return G.page.returnMap(true, "ok");
        } catch (Exception e) {
            return G.page.returnMap(false, "退出失败");
        }

    }

}
