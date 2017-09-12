package cn.wanru.fund.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

/**
 * @author xxf
 * @date 17-9-12
 */
@Component
public class LogListner implements SpiderListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onSuccess(Request request) {

    }

    @Override
    public void onError(Request request) {
        log.error(request.toString());
    }
}
