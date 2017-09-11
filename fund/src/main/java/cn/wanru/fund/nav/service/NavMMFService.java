package cn.wanru.fund.nav.service;

import cn.wanru.fund.nav.dao.NavMMFDao;
import cn.wanru.fund.nav.entity.NavMMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxf
 * @date 17-9-9
 */
@Service
public class NavMMFService {

    @Autowired
    private NavMMFDao navMMFDao;

    public NavMMF findByCodeAndDateAndSource(NavMMF mmf) {
        return navMMFDao.findByCodeAndDateAndSource(
                mmf.getCode(),mmf.getDate(),mmf.getSource());
    }

    public void merge(NavMMF mmf) {
        NavMMF exist = this.findByCodeAndDateAndSource(mmf);
        if (exist == null) {
            navMMFDao.save(mmf);
        }else{
            exist.setYield7Days(mmf.getYield7Days());
            exist.setYield10k(mmf.getYield10k());
            navMMFDao.save(exist);
        }
    }
}
