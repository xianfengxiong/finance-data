package cn.wanru.fund.nav.crawl.sina;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.service.NavService;
import cn.wanru.webmagic.pipeline.ClassBasePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xxf
 * @since 2017/9/12
 */
@Component
public class SINAPipeline extends ClassBasePipeline<List<BaseNav>> {

    @Autowired
    private NavService navService;

    public SINAPipeline() {
        super(SINAUtil.class);
    }

    @Override
    protected void processResult(List<BaseNav> result) {
        result.forEach(navService::merge);
    }

}
