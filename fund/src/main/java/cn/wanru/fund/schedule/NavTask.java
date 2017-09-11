package cn.wanru.fund.schedule;

import cn.wanru.fund.basicinfo.service.FundBasicInfoService;
import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.fund.nav.crawl.ntes.NTESUtil;
import cn.wanru.fund.util.DateUtil;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

/**
 * @author xxf
 * @since 2017/9/11
 */
@Component
public class NavTask {

    @Autowired
    private Spider spider;

    @Autowired
    private FundBasicInfoService fundBasicInfoService;

    public void crawlNavNTES() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.plusDays(5);
        fundBasicInfoService.findAll()
                .forEach(info ->
                        spider.addRequest(
                                createNTESRequests(info.getCode(),
                                        DateUtil.toString(start),
                                        DateUtil.toString(end),
                                        info.getMmf()
                                )
                        )
                );
    }

    private Request createNTESRequests(String code, String start, String end,boolean mmf) {
        PageSizePageable pageable = new PageSizePageable(60);
        pageable.setCode(code);
        pageable.setCurrentPage(0);
        pageable.setCurrentPageSize(0);
        pageable.setMmf(mmf);
        pageable.setStart(start);
        pageable.setEnd(end);
        return NTESUtil.createRequest(pageable);
    }
}
