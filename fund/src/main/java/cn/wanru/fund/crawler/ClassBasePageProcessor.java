package cn.wanru.fund.crawler;

import cn.wanru.webmagic.pageprocessor.SupportablePageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;

import java.util.Collection;

import static cn.wanru.fund.crawler.Util.getSupportClass;
import static cn.wanru.webmagic.PageUtil.setData;

/**
 * @author xxf
 * @date 17-9-9
 */
public abstract class ClassBasePageProcessor<T> extends SupportablePageProcessor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Class<?> supportClass;

    public ClassBasePageProcessor(Class<?> supportClass) {
        this.supportClass = supportClass;
    }

    @Override
    public boolean support(Page page) {
        Class<?> clazz = getSupportClass(page.getRequest());
        return clazz == supportClass;
    }

    @Override
    public void process(Page page) {
        T data = processPage(page);
        setData(data,page);

        if (log.isDebugEnabled()) {
            int size = data instanceof Collection
                    ? ((Collection)data).size() : 1;

            log.debug("url[] crawl success,size=[]",
                    page.getRequest().getUrl(),size);
        }
    }

    protected abstract T processPage(Page page);

}
