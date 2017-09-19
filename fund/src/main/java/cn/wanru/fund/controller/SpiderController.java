package cn.wanru.fund.controller;

import cn.wanru.fund.util.Code;
import cn.wanru.fund.util.JsonResponse;
import cn.wanru.webmagic.DisposableSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;

/**
 * @author xxf
 * @since 2017/9/19
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    private DisposableSpider spider;

    @RequestMapping("/status/page")
    public ModelAndView statusPage() {
        return new ModelAndView("spider-status");
    }


    @RequestMapping("/status")
    public JsonResponse status() {
        MonitorableScheduler scheduler = (MonitorableScheduler) spider.getScheduler();
        int leftRequestsCount = scheduler.getLeftRequestsCount(spider);
        int totalRequestsCount = scheduler.getTotalRequestsCount(spider);
        return new JsonResponse<>(Code.ok,"ok")
                .setData(new SpiderStatusVO(leftRequestsCount,totalRequestsCount));
    }

    private static class SpiderStatusVO {
        private int leftRequestsCount;
        private int totalRequestsCount;

        public SpiderStatusVO(int leftRequestsCount, int totalRequestsCount) {
            this.leftRequestsCount = leftRequestsCount;
            this.totalRequestsCount = totalRequestsCount;
        }

        public int getLeftRequestsCount() {
            return leftRequestsCount;
        }

        public void setLeftRequestsCount(int leftRequestsCount) {
            this.leftRequestsCount = leftRequestsCount;
        }

        public int getTotalRequestsCount() {
            return totalRequestsCount;
        }

        public void setTotalRequestsCount(int totalRequestsCount) {
            this.totalRequestsCount = totalRequestsCount;
        }
    }

}
