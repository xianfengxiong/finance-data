package cn.wanru.fund.crawler.nav;

import cn.wanru.fund.crawler.ClassBasePageProcessor;
import cn.wanru.fund.crawler.PageInfo;
import cn.wanru.fund.entity.nav.BaseNav;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;

import static cn.wanru.fund.crawler.Util.getPageInfo;
import static cn.wanru.fund.crawler.nav.NtesUtil.createRequest;
import static cn.wanru.fund.crawler.nav.NtesUtil.hasNextPage;
import static cn.wanru.fund.crawler.nav.NtesUtil.nextPage;

/**
 * @author xxf
 * @date 17-9-9
 */
@Component
public class NtesPageProcessor extends
        ClassBasePageProcessor<List<BaseNav>> {

    public NtesPageProcessor() {
        super(NtesPageProcessor.class);
    }

    @Override
    protected List<BaseNav> doProcess(Page page) {
        List<BaseNav> result = NtesUtil.parsePage(page);
        if (hasNextPage(result.size())) {
            PageInfo current = getPageInfo(page.getRequest());
            page.addTargetRequest(createRequest(nextPage(current)));
        }

        return result;
    }

}
