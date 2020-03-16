package cn.edu.mju.ccce.dtrsystem.bmo;

import cn.edu.mju.ccce.dtrsystem.bean.Course;
import cn.edu.mju.ccce.dtrsystem.bean.Evaluate;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import cn.edu.mju.ccce.dtrsystem.dao.EvaluateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.bmo.EvaluateBmoImpl<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-03-16 10:15<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.bmo.EvaluateBmoImpl")
public class EvaluateBmoImpl implements EvaluateBmo {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    @Qualifier("cn.edu.mju.ccce.dtrsystem.dao.EvaluateDao")
    protected EvaluateDao evaluateDao;


    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.CourseBmoImpl")
    private CourseBmo courseBmo;

    /**
     * 创建一条新的评价记录
     *
     * @param e
     * @return
     */
    @Override
    public Map<String, Object> createEvaluate(Evaluate e) {
        try {
            Map<String, Object> courseMap = courseBmo.getCourseDetByID(String.valueOf(e.getCOURSE_ID()));
            boolean relMapBoolean = G.bmo.returnMapBool(courseMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(courseMap);
                return G.page.returnMap(false, msg);
            }
            Course c = (Course) MapTool.getObject(courseMap, "courseDet");
            String course_name = c.getCOURSE_NAME();
            e.setCREAT_TIME(new Date());
            e.setCOURSE_NAME(course_name);
            e.setEVALUATE_STATUS(0);
            int insert = evaluateDao.insert(e);
            if (insert > 0) {
                return G.bmo.returnMap(true, "ok");
            }
            return G.bmo.returnMap(false, "创建失败");
        } catch (Exception ex) {
            log.error("创建评价记录异常：", ex);
            return G.bmo.returnMap(false, "创建评价记录异常");
        }
    }
}
