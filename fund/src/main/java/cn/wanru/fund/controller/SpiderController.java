package cn.wanru.fund.controller;

import cn.wanru.webmagic.DisposableSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;

/**
 * @author xxf
 * @date 17-9-18
 */
@RequestMapping("/spider")
@RestController
public class SpiderController {

    @Autowired
    private DisposableSpider spider;

    @RequestMapping("status")
    public String statua() {
        MonitorableScheduler scheduler =
                (MonitorableScheduler) spider.getScheduler();
        int leftRequestsCount = scheduler.getLeftRequestsCount(spider);
        int totalRequestsCount = scheduler.getTotalRequestsCount(spider);
        return "{leftRequestsCount : "+ leftRequestsCount +
                "," +
                "totalRequestsCount : " + totalRequestsCount + "}";
    }

}
