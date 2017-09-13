import cn.wanru.fund.crawler.PageSizePageable;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

/**
 * @author xxf
 * @date 17-9-13
 */
public class PageSizePageableTest {

    @Test
    public void testSerializePageSizePageable() {
        PageSizePageable bean = new PageSizePageable(10);
        PageSizePageable clone = SerializationUtils.clone(bean);
        System.out.println(clone);
    }
}
