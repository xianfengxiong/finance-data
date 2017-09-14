package cn.wanru.fund.nav.crawl.tencent;

import cn.wanru.fund.crawler.GenericPageable;

/**
 * @author xxf
 * @since 2017/9/14
 */
public class TencentPageable extends GenericPageable {

    private boolean yield7Days;

    public boolean isYield7Days() {
        return yield7Days;
    }

    public void setYield7Days(boolean yield7Days) {
        this.yield7Days = yield7Days;
    }
}
