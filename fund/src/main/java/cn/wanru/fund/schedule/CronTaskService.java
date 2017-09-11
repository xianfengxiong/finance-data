package cn.wanru.fund.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxf
 * @since 2017/9/11
 */
@Service
public class CronTaskService {

    @Autowired
    private CronTaskDao cronTaskDao;

    public List<CronTask> findAll() {
        return cronTaskDao.findAll();
    }

}
