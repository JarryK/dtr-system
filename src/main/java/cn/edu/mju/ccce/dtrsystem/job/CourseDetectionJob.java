package cn.edu.mju.ccce.dtrsystem.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.job.CourseDetectionJob<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>定时任务，用来更新课程的状态（到课程时间自动不能预约）<br>
 * <b>创建时间：</b>2020-03-13 15:16<br>
 */
@Component
public class CourseDetectionJob implements SchedulingConfigurer, Runnable, Trigger {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {
        log.info("执行检测任务："+new Date());
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        String cronString = "0 0/1 * * * ?";
        log.debug("课程检测job任务时间cron表达式设定={}", cronString);
        return new CronTrigger(cronString).nextExecutionTime(triggerContext);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(this, this);
    }
}
