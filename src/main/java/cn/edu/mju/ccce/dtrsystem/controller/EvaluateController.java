package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bean.EvaluateStu;
import cn.edu.mju.ccce.dtrsystem.bean.EvaluateTea;
import cn.edu.mju.ccce.dtrsystem.bean.User;
import cn.edu.mju.ccce.dtrsystem.bmo.EvaluateBmo;
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
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.EvaluateController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>评价系统Controller<br>
 * <b>创建时间：</b>2020-03-15 15:15<br>
 */
@Controller
@RequestMapping("/dtr/eval")
public class EvaluateController {
    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.EvaluateBmoImpl")
    private EvaluateBmo evaluateBmo;

    /**
     * 新增评价
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String courseID = MapTool.getString(inMap, "courseID");
        String evaluateDetail = MapTool.getString(inMap, "evaluateDetail");
        String evaluateScore = MapTool.getString(inMap, "evaluateScore");
        try {
            courseID.substring(1);//探测非空
            evaluateDetail.substring(1);
            evaluateScore.substring(1);
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空！");
        }
        try {
            Map<String, Object> uMsg = User.getUserMap(httpSession);
            if (uMsg.isEmpty()) {
                return G.page.returnMap(false, "请先登录");
            }
            String userName = MapTool.getString(uMsg, "USER_NAME");
            String userNbr = MapTool.getString(uMsg, "USER_NBR");
            String typeName = MapTool.getString(uMsg, "TYPE_NAME");
            if ("学生".equals(typeName)) {
                EvaluateStu e = new EvaluateStu();
                e.setCOURSE_ID(Long.parseLong(courseID));
                e.setEVALUATE_DETAIL(evaluateDetail);
                e.setEVALUATE_SCORE(Double.parseDouble(evaluateScore));
                e.setUSER_NBR(Integer.parseInt(userNbr));
                e.setUSER_NAME(userName);
                Map<String, Object> relMap = evaluateBmo.createStudentEvaluate(e);
                boolean relMapBoolean = G.bmo.returnMapBool(relMap);
                if (!relMapBoolean) {
                    String msg = G.bmo.returnMapMsg(relMap);
                    return G.page.returnMap(false, msg);
                }
                return G.page.returnMap(true, "ok");
            } else if ("教师".equals(typeName)) {
                String stuNbr = MapTool.getString(inMap, "stuNbr");
                EvaluateTea e = new EvaluateTea();
                e.setCOURSE_ID(Long.parseLong(courseID));
                e.setEVALUATE_DETAIL(evaluateDetail);
                e.setEVALUATE_SCORE(Double.parseDouble(evaluateScore));
                e.setUSER_NBR(Integer.parseInt(stuNbr));
                e.setCOURSE_TEACHER_NAME(userName);
                e.setCOURSE_TEACHER_NBR(BigInteger.valueOf(Long.parseLong(userNbr)));
                Map<String, Object> relMap = evaluateBmo.createTeacherEvaluate(e);
                boolean relMapBoolean = G.bmo.returnMapBool(relMap);
                if (!relMapBoolean) {
                    String msg = G.bmo.returnMapMsg(relMap);
                    return G.page.returnMap(false, msg);
                }
                return G.page.returnMap(true, "ok");
            } else {
                return G.page.returnMap(false, "用户权限错误！新建失败");
            }
        } catch (Exception e) {
            log.error("新建评价异常", e);
            return G.page.returnMap(false, "新建评价异常");
        }
    }

    /**
     * 查看评价
     *
     * @param inMap
     * @param httpSession
     * @return
     */
    @RequestMapping("/view")
    @ResponseBody
    public Map<String, Object> view(@RequestBody Map<String, Object> inMap, HttpSession httpSession) {
        String courseID = MapTool.getString(inMap, "courseID");
        try {
            courseID.substring(1);//探测非空
        } catch (Exception e) {
            return G.page.returnMap(false, "输入为空！");
        }
        try {
            Map<String, Object> uMsg = User.getUserMap(httpSession);
            if (uMsg.isEmpty()) {
                return G.page.returnMap(false, "请先登录");
            }
            String userNbr = MapTool.getString(uMsg, "USER_NBR");
            String userType = MapTool.getString(uMsg, "TYPE_NAME");
            if ("学生".equals(userType)) {
                Map<String, Object> relMap = evaluateBmo.getStudentEvaluate(courseID, userNbr);
                boolean relMapBoolean = G.bmo.returnMapBool(relMap);
                if (!relMapBoolean) {
                    String msg = G.bmo.returnMapMsg(relMap);
                    return G.page.returnMap(false, msg);
                }
                EvaluateStu e = (EvaluateStu) MapTool.getObject(relMap, "EvaluateStu");
                Map<String, Object> returnMap = G.page.returnMap(true, "ok");
                returnMap.put("evaluateStu", e);
                return returnMap;
            } else if ("教师".equals(userType)) {
                String stuNbr = MapTool.getString(inMap, "stuNbr");
                Map<String, Object> relMap = evaluateBmo.getTeacherEvaluate(courseID, stuNbr);
                boolean relMapBoolean = G.bmo.returnMapBool(relMap);
                if (!relMapBoolean) {
                    String msg = G.bmo.returnMapMsg(relMap);
                    return G.page.returnMap(false, msg);
                }
                EvaluateStu e = (EvaluateStu) MapTool.getObject(relMap, "EvaluateTea");
                Map<String, Object> returnMap = G.page.returnMap(true, "ok");
                returnMap.put("evaluateStu", e);
                return returnMap;
            } else {
                return G.page.returnMap(false, "用户权限错误！查询失败");
            }
        } catch (Exception e) {
            log.error("查询评价异常", e);
            return G.page.returnMap(false, "查询评价异常");
        }
    }

}
