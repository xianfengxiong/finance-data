package cn.wanru.fund.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @since 2017/9/11
 */
public interface CronTaskDao extends JpaRepository<CronTask,Long> {
}
