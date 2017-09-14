package cn.wanru.fund.nav.crawl.tencent;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.service.NavService;
import cn.wanru.webmagic.pipeline.ClassBasePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.List;

import static cn.wanru.webmagic.PageUtil.getData;
import static cn.wanru.webmagic.PageUtil.getPageable;

/**
 * @author xxf
 * @since 2017/9/14
 */
@Service
public class TencentPipeline extends ClassBasePipeline<List<BaseNav>> {

    @Autowired
    private TencentMMFNavService tencentNavService;

    @Autowired
    private NavService navService;

    public TencentPipeline() {
        super(TencentUtil.class);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        TencentPageable pageable = (TencentPageable) getPageable(resultItems.getRequest());
        List<BaseNav> result = getData(resultItems);
        for (BaseNav baseNav : result) {
            if (baseNav instanceof NavMMF) {
                tencentNavService.mergeNavMMF((NavMMF)baseNav,pageable);
            }else{
                navService.merge(baseNav);
            }
        }
    }

    @Override
    protected void processResult(List<BaseNav> result) {
        // do nothing
    }

}
