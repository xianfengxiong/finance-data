package cn.wanru.fund.nav.crawl.sina;

import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.webmagic.Pageable;
import cn.wanru.webmagic.pageprocessor.ClassBasePageablePageProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.List;

/**
 * @author xxf
 * @since 2017/9/12
 */
@Component
public class SINAPageProcessor extends ClassBasePageablePageProcessor<List<BaseNav>> {

    public SINAPageProcessor() {
        super(SINAUtil.class);
    }

    @Override
    protected Request createRequest(Pageable pageable) {
        return SINAUtil.createRequest((GenericPageable) pageable);
    }

    @Override
    protected List<BaseNav> parsePage(Page page, Pageable pageable) {
        return SINAUtil.parsePage(page,pageable);
    }

    @Override
    protected void updatePageable(List<BaseNav> result, Pageable pageable) {
        ((GenericPageable) pageable).setCurrentPageSize(result.size());
    }
}
