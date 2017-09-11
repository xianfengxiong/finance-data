package cn.wanru.fund.schedule;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xxf
 * @since 2017/9/11
 */
@Component
public class ScheduleTaskConfigurer implements
        SchedulingConfigurer,ApplicationContextAware {

    @Autowired
    private CronTaskService cronTaskService;

    private ApplicationContext applicationContext;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<cn.wanru.fund.schedule.CronTask> cronTasks = cronTaskService.findAll();
        for (cn.wanru.fund.schedule.CronTask task : cronTasks) {
            try {
                taskRegistrar.addCronTask(createCronTask(task));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private CronTask createCronTask(cn.wanru.fund.schedule.CronTask task)
            throws NoSuchMethodException {
        String cron = task.getCron();
        String beanName = task.getBeanName();
        Object bean = applicationContext.getBean(beanName);
        String methodName = task.getMethodName();
        Method method = bean.getClass().getMethod(methodName);
        Runnable runnable = new ScheduledMethodRunnable(bean,method);
        return new CronTask(runnable,cron);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

}
