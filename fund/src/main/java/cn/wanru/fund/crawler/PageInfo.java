package cn.wanru.fund.crawler;

/**
 * @author xxf
 * @date 17-9-9
 */
public class PageInfo {

    private String code;

    private String start;

    private String end;

    private int currentPage;

    private boolean mmf;

    public PageInfo() {
    }

    public PageInfo(String code, String start,
            String end, int currentPage, boolean mmf) {
        this.code = code;
        this.start = start;
        this.end = end;
        this.currentPage = currentPage;
        this.mmf = mmf;
    }

    public String getCode() {
        return code;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean isMmf() {
        return mmf;
    }

}
