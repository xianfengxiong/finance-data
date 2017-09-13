package cn.wanru.fund.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @since 2017/9/13
 */
public interface CronTaskBeanDao extends JpaRepository<CronTaskBean,Long> {
}
