package cn.wanru.fund.crawler.nav;

import cn.wanru.fund.crawler.ClassBasePipeline;
import cn.wanru.fund.entity.nav.BaseNav;
import cn.wanru.fund.service.nav.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author xxf
 * @date 17-9-9
 */
@Component
public class NtesPipeline extends ClassBasePipeline<List<BaseNav>> {

    @Autowired
    private NavService navService;

    public NtesPipeline() {
        super(NtesPageProcessor.class);
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
