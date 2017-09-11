package cn.wanru.fund.nav.crawl.ntes;

import cn.wanru.fund.crawler.ClassBasePipeline;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author xxf
 * @date 17-9-9
 */
@Component
public class NTESPipeline extends ClassBasePipeline<List<BaseNav>> {

    @Autowired
    private NavService navService;

    public NTESPipeline() {
        super(NTESPageProcessor.class);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void doProcess(List<BaseNav> navs) {
        navs.forEach(nav -> navService.merge(nav));
    }
}
