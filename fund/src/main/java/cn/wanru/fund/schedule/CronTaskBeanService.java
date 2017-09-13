package cn.wanru.fund.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxf
 * @since 2017/9/13
 */
@Service
public class CronTaskBeanService {

    @Autowired
    private CronTaskBeanDao cronTaskBeanDao;

    public List<CronTaskBean> findAll() {
        return cronTaskBeanDao.findAll();
    }
}
