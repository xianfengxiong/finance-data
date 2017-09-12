package cn.wanru.fund.crawler;

import cn.wanru.webmagic.Pageable;

/**
 * @author xxf
 * @date 17-9-10
 */
public class GenericPageable implements Pageable, Cloneable {

    private String code;

    private String start;

    private String end;

    private int currentPage;

    private int currentPageSize;

    private int totalPage;

    private boolean mmf;

    // region Getter/Setter

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isMmf() {
        return mmf;
    }

    public void setMmf(boolean mmf) {
        this.mmf = mmf;
    }

// endregion


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Pageable next() {
        try {
            GenericPageable clone = (GenericPageable) clone();
            clone.setTotalPage(0);
            clone.setCurrentPageSize(0);
            clone.setCurrentPage(getCurrentPage() + 1);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "GenericPageable{" +
                "code='" + code + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", currentPage=" + currentPage +
                ", currentPageSize=" + currentPageSize +
                ", totalPage=" + totalPage +
                ", mmf=" + mmf +
                '}';
    }
}
