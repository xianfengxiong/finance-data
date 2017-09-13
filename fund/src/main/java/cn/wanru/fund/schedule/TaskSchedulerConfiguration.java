package cn.wanru.fund.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author xxf
 * @since 2017/9/13
 */
@Configuration
public class TaskSchedulerConfiguration {

    private int processorNum = Runtime.getRuntime().availableProcessors();

    @Bean
    public TaskScheduler taskScheduler() {
        ScheduledExecutorService service =
                Executors.newScheduledThreadPool(processorNum * 2);

        return new ConcurrentTaskScheduler(service);
    }

}
