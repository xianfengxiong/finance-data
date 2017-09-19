package cn.wanru.fund.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

/**
 * @author xxf
 * @since 2017/9/13
 */
@Component
public class TaskConfigurer implements SchedulingConfigurer, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CronTaskBeanService cronTaskBeanService;

    private ApplicationContext applicationContext;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<CronTaskBean> taskBeans = cronTaskBeanService.findAll();
        for (CronTaskBean taskBean : taskBeans) {
            String cron = taskBean.getCron();
            Optional<ScheduledMethodRunnable> runnable = getRunnable(taskBean);
            if (runnable.isPresent()) {
                taskRegistrar.addCronTask(new CronTask(runnable.get(), cron));
                log.info("registry cron task[{}] success,cron=[{}]", taskBean.getName(),taskBean.getCron());
            } else {
                log.warn("cron task[{}] registry failed,cron=[{}]",taskBean.getName(),taskBean.getCron());
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        this.applicationContext = applicationContext;
    }

    private Optional<ScheduledMethodRunnable> getRunnable(CronTaskBean taskBean) {
        String beanName = taskBean.getBeanName();
        String methodName = taskBean.getMethodName();
        Object bean = applicationContext.getBean(beanName);

        if (bean != null) {
            try {
                Method method = bean.getClass().getMethod(methodName);
                ScheduledMethodRunnable r = new ScheduledMethodRunnable(bean,method);
                return Optional.of(r);
            }catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.empty();
    }
}
