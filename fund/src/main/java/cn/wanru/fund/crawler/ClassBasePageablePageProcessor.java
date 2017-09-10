package cn.wanru.fund.crawler;

import cn.wanru.webmagic.Pageable;
import cn.wanru.webmagic.pageprocessor.PageablePageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.Collection;

import static cn.wanru.fund.crawler.Util.getSupportClass;
import static cn.wanru.webmagic.PageUtil.setData;

/**
 * @author xxf
 * @date 17-9-10
 */
public abstract class ClassBasePageablePageProcessor<T>
        extends PageablePageProcessor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Class<?> supportClass;

    public ClassBasePageablePageProcessor(Class<?> supportClass) {
        this.supportClass = supportClass;
    }

    @Override
    public boolean support(Page page) {
        Class<?> clazz = getSupportClass(page.getRequest());
        return clazz == supportClass;
    }

    @Override
    protected void doProcess(Page page) {
        T data = processInternal(page);
        setData(data,page);

        if (log.isDebugEnabled()) {
            int size = data instanceof Collection
                    ? ((Collection)data).size() : 1;

            log.debug("url[] crawl success,size=[]",
                    page.getRequest().getUrl(),size);
        }
    }

    protected abstract T processInternal(Page page) ;
}
