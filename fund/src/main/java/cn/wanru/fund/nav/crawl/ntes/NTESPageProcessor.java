package cn.wanru.fund.nav.crawl.ntes;

import cn.wanru.fund.crawler.ClassBasePageablePageProcessor;
import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.webmagic.Pageable;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.List;

/**
 * @author xxf
 * @date 17-9-9
 */
@Component
public class NTESPageProcessor extends
    ClassBasePageablePageProcessor<List<BaseNav>> {

    public NTESPageProcessor() {
        super(NTESPageProcessor.class);
    }

    @Override
    protected Request createRequest(Pageable pageable) {
        GenericPageable genericPageable = (GenericPageable) pageable;
        return NTESUtil.createRequest(genericPageable);
    }


    @Override
    protected List<BaseNav> processPage(Page page,Pageable pageable) {
        return NTESUtil.parsePage(page,pageable);
    }

    @Override
    protected void updatePageable(List<BaseNav> data, Pageable pageable) {
        ((GenericPageable) pageable).setCurrentPageSize(data.size());
    }
}
