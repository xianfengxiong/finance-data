package cn.wanru.fund.crawler.nav;

import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.webmagic.Pageable;

/**
 * @author xxf
 * @date 17-9-10
 */
public class NtesPageable extends GenericPageable implements Pageable {

    @Override
    public boolean hasNext() {
        return this.getCurrentPageSize() == 60;
    }

    @Override
    public Pageable next() {
        try {
            return (Pageable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
