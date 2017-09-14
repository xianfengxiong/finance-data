package cn.wanru.fund.nav.crawl.tencent;

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
 * @since 2017/9/14
 */
public class TencentUtil {

    private static final String mmf_yield_10k_url_template = "http://stockjs.finance.qq.com/hbFundNavAll/data/profit_year_all/%s.js";

    private static final String mmf_yield_7days_url_template = "http://stockjs.finance.qq.com/hbFundNavAll/data/yearly_profit_pct_year_all/%s.js";

    private static final String nmf_url_template = "http://stockjs.finance.qq.com/fundUnitNavAll/data/year_all/%s.js";



    private TencentUtil() {

    }


    public static TencentPageable createPageable(
            String code, boolean mmf, boolean yield7Days ){
        TencentPageable pageable = new TencentPageable();
        pageable.setCode(code);
        pageable.setMmf(mmf);
        pageable.setYield7Days(yield7Days);
        return pageable;
    }

    public static Request createRequest(TencentPageable pageable) {
        String url = null;
        if (pageable.isMmf()) {
            if (pageable.isYield7Days()) {
                url = String.format(mmf_yield_7days_url_template,pageable.getCode());
            } else {
                url = String.format(mmf_yield_10k_url_template,pageable.getCode());
            }
        }else{
            url = String.format(nmf_url_template,pageable.getCode());
        }
        Request request = new Request(url);
        setPageable(request,pageable);
        setSupportClass(request,TencentUtil.class);
        return request;
    }

    public static List<BaseNav> parsePage(Page page, Pageable pageable) {
        TencentPageable tencentPageable = (TencentPageable) pageable;
        String code = tencentPageable.getCode();
        String text = page.getRawText();
        int equalIndex = text.indexOf("=");
        if (equalIndex == -1) {
            throw new ParsePageException(text);
        }
        String json = text.substring(equalIndex + 1);
        TencentResult tencentResult = JsonUtil.fromJson(json, TencentResult.class);
        List<BaseNav> baseNavList = tencentResult.toBaseNavList(tencentPageable);
        baseNavList.forEach( e -> {e.setCode(code);e.setSource(NavSource.tencent);});
        return baseNavList;
    }

}
