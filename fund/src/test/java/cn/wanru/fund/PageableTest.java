package cn.wanru.fund;

import cn.wanru.fund.crawler.nav.NtesPageable;
import cn.wanru.webmagic.Pageable;
import org.junit.Test;

/**
 * @author xxf
 * @date 17-9-10
 */
public class PageableTest {

    @Test
    public void testClone() {
        NtesPageable pageable = new NtesPageable();
        pageable.setCurrentPage(0);
        pageable.setCurrentPageSize(60);
        Pageable next = pageable.next();
        System.out.println(pageable);
        System.out.println(next);
    }

}
