package cn.wanru.fund.crawler;

import cn.wanru.webmagic.pipeline.SupportablePipeline;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import static cn.wanru.fund.crawler.Util.getSupportClass;
import static cn.wanru.webmagic.PageUtil.getData;

/**
 * @author xxf
 * @date 17-9-9
 */
public abstract class ClassBasePipeline<T> implements SupportablePipeline {

    private Class<?> supportClass;

    public ClassBasePipeline(Class<?> supportClass) {
        this.supportClass = supportClass;
    }

    @Override
    public boolean support(ResultItems resultItems) {
        return getSupportClass(resultItems.getRequest()) == supportClass;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        doProcess(getData(resultItems));
    }

    protected abstract void doProcess(T data);
}
