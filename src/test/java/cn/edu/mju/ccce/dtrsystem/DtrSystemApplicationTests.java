package cn.edu.mju.ccce.dtrsystem;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DtrSystemApplicationTests {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    void contextLoads() {
        String dateStr = "2020-03-19";
        String dateStr2 = "2020-04-19";
        //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sf.parse(dateStr);
            Date date =sf.parse(dateStr2);
            List<Date> list = getBetweenDates(date1,date);
            log.debug(String.valueOf(list));
        } catch (ParseException e) {
            e.printStackTrace();

        }
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
//    Calendar calendar = Calendar.getInstance(); calendar.setTime(minDate); //清零 时分秒毫秒； 因为数据库返回的是没有这些的；是按照天分组
//    calendar.set(Calendar.HOUR_OF_DAY, 0); calendar.set(Calendar.MINUTE, 0);
//    calendar.set(Calendar.SECOND, 0);
//    calendar.set(Calendar.MILLISECOND, 0);
//    int i = 0;//存在list的第几个位置
//    while (calendar.getTime().getTime() <= request.getMaxDate().getTime()) { //补全天数
//         if (null == dateMap.get(calendar.getTime())) { vo = new OrderAnalysisTotalForIndexVO(); vo.setSyncDate(calendar.getTime()); list.add(i, vo); } calendar.add(Calendar.DAY_OF_MONTH, 1); //设置后一天 i ++; }

}
