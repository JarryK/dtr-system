package cn.edu.mju.ccce.dtrsystem.dao;

import cn.edu.mju.ccce.dtrsystem.bean.Evaluate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.dao.EvaluateDao<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020-03-16 11:24<br>
 */
@Service("cn.edu.mju.ccce.dtrsystem.dao.EvaluateDao")
public interface EvaluateDao {

    /**
     * 新建一条评论记录
     * @param evaluate
     * @return
     */
    public int insert (Evaluate evaluate);
}
