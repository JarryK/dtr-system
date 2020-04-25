package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.User;
import cn.edu.mju.ccce.dtrsystem.bmo.UserBmo;
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
import java.math.BigInteger;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.UserController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-04-08 20:48<br>
 */
@Controller
@RequestMapping("/dtr/user")
public class UserController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.UserBmoImpl")
    private UserBmo userBmo;

    @RequestMapping("getUserMsg")
    @ResponseBody
    public Map<String, Object> getUser(HttpSession httpSession) {
        try {
            Map<String, Object> uMsg = User.getUserMap(httpSession);
            if (uMsg.isEmpty()) {
                return G.page.returnMap(false, "请先登录");
            }
            String userNbr = MapTool.getString(uMsg, "USER_NBR");
            if("".equals(userNbr)){
                return G.page.returnMap(false, "请先登录");
            }
            Map<String, Object> relMap = userBmo.selectUserByUserNbr(userNbr);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            User user = (User) MapTool.getObject(relMap, "user");
            Map<String, Object> returnMap = G.page.returnMap(true, "ok");
            returnMap.put("user", user);
            return returnMap;
        } catch (Exception e) {
            log.error("用户查找异常", e);
            return G.page.returnMap(false, "用户查找异常");
        }
    }

    @RequestMapping("changeSelfMsg")
    @ResponseBody
    public Map<String, Object> changeSelfMsg(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        try {
            Map<String, Object> uMsg = User.getUserMap(httpSession);
            if (uMsg.isEmpty()) {
                return G.page.returnMap(false, "请先登录");
            }
            String userNbr = MapTool.getString(uMsg, "USER_NBR");
            Map<String, Object> relMap = userBmo.selectUserByUserNbr(userNbr);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                return G.page.returnMap(false, msg);
            }
            User user = (User) MapTool.getObject(relMap, "user");
            String sex = MapTool.getString(inMap, "sex");
            String phone = MapTool.getString(inMap, "phone");
            String pass = MapTool.getString(inMap, "pass");
            if (!("".equals(sex) || sex == null)) {
                user.setUSER_SEX(sex);
            }
            if (!("".equals(phone) || phone == null)) {
                user.setUSER_PHONE(BigInteger.valueOf(Integer.valueOf(phone)));
            }
            if (!("".equals(pass) || pass == null)) {
                user.setUSER_PASS(pass);
            }
            Map<String, Object> changeMap = userBmo.upDataUser(user);
            boolean changeMapMapBoolean = G.bmo.returnMapBool(changeMap);
            if (!changeMapMapBoolean) {
                String msg = G.bmo.returnMapMsg(changeMap);
                return G.page.returnMap(false, msg);
            }
            return G.page.returnMap(true, "ok");
        } catch (Exception e) {
            log.error("用户查找异常", e);
            return G.page.returnMap(false, "用户查找异常");
        }
    }

}
