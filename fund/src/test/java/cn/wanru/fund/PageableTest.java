package cn.wanru.fund;

import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.webmagic.Pageable;
import org.junit.Test;

/**
 * @author xxf
 * @date 17-9-10
 */
public class PageableTest {

    @Test
    public void testClone() {
        PageSizePageable pageable = new PageSizePageable(60);
        pageable.setCurrentPage(0);
        pageable.setCurrentPageSize(60);
        Pageable next = pageable.next();
        System.out.println(pageable);
        System.out.println(next);
    }

}
