package cn.wanru.fund.nav.crawl.sina;

import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.fund.crawler.ParsePageException;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavSource;
import cn.wanru.fund.util.JsonUtil;
import cn.wanru.webmagic.Pageable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.List;

import static cn.wanru.webmagic.PageUtil.setPageable;
import static cn.wanru.webmagic.PageUtil.setSupportClass;

/**
 * @author xxf
 * @since 2017/9/12
 */
public class SINAUtil {

    // page 从 1 开始
    private static final String mmf_url_template = "http://stock.finance.sina.com.cn/fundInfo/api/openapi.php/CaihuiFundInfoService.getNavcur?symbol=%s&datefrom=%s&dateto=%s&page=%s";

    private static final String nmf_url_template = "http://stock.finance.sina.com.cn/fundInfo/api/openapi.php/CaihuiFundInfoService.getNav?symbol=%s&datefrom=%s&dateto=%s&page=%s";


    public static PageSizePageable createPageable(
            String code, boolean mmf, String start, String end) {

        PageSizePageable pageable = new PageSizePageable(20);
        pageable.setCode(code);
        pageable.setCurrentPage(1);
        pageable.setMmf(mmf);
        pageable.setStart(start);
        pageable.setEnd(end);
        return pageable;
    }

    public static Request createRequest(GenericPageable pageable) {
        String url = null;
        if (pageable.isMmf()) {
            url = String.format(mmf_url_template, pageable.getCode(),
                    pageable.getStart(), pageable.getEnd(),
                    pageable.getCurrentPage());
        } else {
            url = String.format(nmf_url_template,pageable.getCode(),
                    pageable.getStart(),pageable.getEnd(),pageable.getCurrentPage());
        }
        Request request = new Request(url);
        setPageable(request, pageable);
        setSupportClass(request, SINAUtil.class);
        return request;
    }

    public static List<BaseNav> parsePage(Page page, Pageable pageable) {
        GenericPageable genericPageable = (GenericPageable) pageable;
        String json = page.getRawText();
        JsonResult result = JsonUtil.fromJson(json, JsonResult.class);
        if (!result.isSuccess()) {
            throw new ParsePageException(result.toString());
        }
        List<BaseNav> baseNavList = result.toBaseNavList(genericPageable.isMmf());
        baseNavList.forEach(nav -> {
            nav.setCode(genericPageable.getCode());
            nav.setSource(NavSource.sina);
        });
        return baseNavList;
    }

}
