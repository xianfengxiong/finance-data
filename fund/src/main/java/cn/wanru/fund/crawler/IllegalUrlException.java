package cn.wanru.fund.crawler;

/**
 * @author xxf
 * @date 17-9-9
 */
public class IllegalUrlException extends RuntimeException {

    public IllegalUrlException(String url) {
        super(url);
    }
}
