package cn.wanru.fund.nav.crawl.tencent;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.webmagic.pageprocessor.ClassBaseSupportablePageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;

import static cn.wanru.webmagic.PageUtil.getPageable;
import static cn.wanru.webmagic.PageUtil.setData;

/**
 * @author xxf
 * @since 2017/9/14
 */
@Component
public class TencentPageProcessor extends ClassBaseSupportablePageProcessor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public TencentPageProcessor() {
        super(TencentUtil.class);
    }

    @Override
    public void process(Page page) {
        List<BaseNav> baseNavList = TencentUtil.parsePage(page, getPageable(page.getRequest()));
        setData(baseNavList, page);

        if (log.isDebugEnabled()) {
            log.debug("url[{}] crawl success, size=[{}]",
                    page.getRequest().getUrl(), baseNavList.size());
        }
    }
}
