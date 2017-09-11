package cn.wanru.fund.nav.service;

import cn.wanru.fund.nav.dao.NavNMFDao;
import cn.wanru.fund.nav.entity.NavNMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxf
 * @date 17-9-9
 */
@Service
public class NavNMFService {

    @Autowired
    private NavNMFDao navNMFDao;

    public NavNMF findByCodeAndDateAndSource(NavNMF bean) {
        return navNMFDao.findByCodeAndDateAndSource(
                bean.getCode(),bean.getDate(),bean.getSource());
    }

    public void merge(NavNMF nmf) {
        NavNMF exist = this.findByCodeAndDateAndSource(nmf);
        if (exist == null) {
            navNMFDao.save(nmf);
        }else{
            exist.setUnitNav(nmf.getUnitNav());
            exist.setAccumNav(nmf.getAccumNav());
            navNMFDao.save(exist);
        }
    }

}
