package cn.wanru.fund.crawler;

/**
 * @author xxf
 * @since 2017/9/11
 */
public class PageSizePageable
        extends GenericPageable
        /*implements Serializable*/ {

    private int pageSize;

    public PageSizePageable(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean hasNext() {
        return pageSize == getCurrentPageSize();
    }

    @Override
    public String toString() {
        return "PageSizePageable{" +
                "code='" + getCode() + '\'' +
                ", start='" + getStart() + '\'' +
                ", end='" + getEnd() + '\'' +
                ", currentPage=" + getCurrentPage() +
                ", currentPageSize=" + getCurrentPageSize() +
                ", totalPage=" + getTotalPage() +
                ", mmf=" + isMmf() +
                ", pageSize=" + pageSize +
                '}';
    }
}
