package cn.wanru.fund.nav.crawl.ntes;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.service.NavService;
import cn.wanru.webmagic.pipeline.ClassBasePipeline;
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
        super(NTESUtil.class);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void processResult(List<BaseNav> navs) {
        navs.forEach(navService::merge);
    }

}
