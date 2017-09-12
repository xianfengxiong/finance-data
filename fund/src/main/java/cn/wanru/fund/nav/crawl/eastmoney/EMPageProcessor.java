package cn.wanru.fund.nav.crawl.eastmoney;

import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.webmagic.Pageable;
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
 * @since 2017/9/12
 */
@Component
public class EMPageProcessor extends ClassBaseSupportablePageProcessor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public EMPageProcessor() {
        super(EMUtil.class);
    }

    @Override
    public void process(Page page) {
        Pageable pageable = getPageable(page.getRequest());
        List<BaseNav> navList = EMUtil.parsePage(page, (GenericPageable) pageable);
        setData(navList,page);

        if (log.isDebugEnabled()) {
            log.debug("url[{}] crawl success,size=[{}]",
                    page.getRequest().getUrl(),navList.size());
        }
    }
}
