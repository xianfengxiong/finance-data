package cn.wanru.fund.nav.service;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.entity.NavNMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxf
 * @date 17-9-9
 */
@Service
public class NavService {

    @Autowired
    private NavMMFService mmfService;

    @Autowired
    private NavNMFService nmfService;

    public void merge(BaseNav nav) {
        if (nav == null) {
            return;
        }

        if (nav instanceof NavMMF)
            mmfService.merge((NavMMF) nav);
        else if (nav instanceof NavNMF)
            nmfService.merge((NavNMF) nav);
        else
            throw new RuntimeException();
    }

}
