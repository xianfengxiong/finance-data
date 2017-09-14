package cn.wanru.fund.schedule;

import cn.wanru.fund.nav.crawl.CrawlRegistry;
import cn.wanru.fund.util.DateUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xxf
 * @since 2017/9/13
 */
@Component
public class NavTask {

    @Autowired
    private CrawlRegistry crawlRegistry;

    public void crawlNavNTES() {
        Pair<String,String> pair = getDateRange();
        crawlRegistry.addNTESRequest(null, pair.getLeft(), pair.getRight());
    }

    public void crawlNavSINA() {
        Pair<String,String> pair = getDateRange();
        crawlRegistry.addNTESRequest(null, pair.getLeft(), pair.getRight());
    }

    public void crawlNavEM() {
        Pair<String,String> pair = getDateRange();
        crawlRegistry.addNTESRequest(null, pair.getLeft(), pair.getRight());
    }

    public void crawlNavTENCENT() {
        crawlRegistry.addTencentRequest(null,true);
        crawlRegistry.addTencentRequest(null,false);
    }

    private Pair<String, String> getDateRange() {
        String start = DateUtil.toString(LocalDate.now().minusDays(5));
        String end = DateUtil.toString(LocalDate.now());
        return Pair.of(start, end);
    }
}
