package cn.wanru.fund.crawler;

import cn.wanru.webmagic.PageUtil;
import us.codecraft.webmagic.Request;

import static cn.wanru.fund.crawler.Constant.support_key;

/**
 * @author xxf
 * @date 17-9-9
 */
public class Util {

    private Util() {

    }

    public static void setSupportClass(Request request,Class<?> clazz) {
        PageUtil.setField(support_key,clazz,request);
    }

    public static Class<?> getSupportClass(Request request) {
        return PageUtil.getField(support_key,request);
    }

}
