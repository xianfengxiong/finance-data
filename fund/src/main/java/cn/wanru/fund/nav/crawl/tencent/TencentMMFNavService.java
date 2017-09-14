package cn.wanru.fund.nav.crawl.tencent;

import cn.wanru.fund.nav.dao.NavMMFDao;
import cn.wanru.fund.nav.entity.NavMMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxf
 * @since 2017/9/14
 */
@Service
public class TencentMMFNavService {

    @Autowired
    private NavMMFDao navMMFDao;

    public void mergeNavMMF(NavMMF navMMF, TencentPageable pageable) {
        NavMMF exist = navMMFDao.findByCodeAndDateAndSource(
                navMMF.getCode(), navMMF.getDate(), navMMF.getSource());

        if (exist == null) {
            navMMFDao.save(navMMF);
        } else {
            if (pageable.isYield7Days()) {
                exist.setYield7Days(navMMF.getYield7Days());
            } else {
                exist.setYield10k(navMMF.getYield10k());
            }
            navMMFDao.save(exist);
        }
    }
}
