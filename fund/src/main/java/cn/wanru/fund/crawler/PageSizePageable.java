package cn.wanru.fund.crawler;

/**
 * @author xxf
 * @since 2017/9/11
 */
public class PageSizePageable extends GenericPageable {

    private int pageSize;

    public PageSizePageable(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean hasNext() {
        return pageSize == getCurrentPageSize();
    }
}
