package cn.wanru.fund.crawler.nav;

import cn.wanru.fund.crawler.*;
import cn.wanru.fund.entity.nav.BaseNav;
import cn.wanru.fund.entity.nav.NavNMF;
import cn.wanru.fund.entity.nav.NavSource;
import cn.wanru.fund.util.DateUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Selectable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.wanru.fund.crawler.Util.getPageInfo;
import static cn.wanru.fund.crawler.Util.setPageInfo;
import static cn.wanru.fund.crawler.Util.setSupportClass;

/**
 * @author xxf
 * @date 17-9-9
 */
public class NtesUtil {

    /*
     * "http://quotes.money.163.com/fund/jzzs_150240_0.html?
     * start=2016-04-01&end=2016-11-11&sort=TDATE&order=desc"
     */
    private static final String url_template =
            "http://quotes.money.163.com/fund/jzzs_%s_%s.html?" +
                    "start=%s&end=%s&sort=TDATE&order=desc";

    private static final Pattern url_pattern = Pattern.compile(
            "http://quotes\\.money\\.163\\.com/fund/jzzs_(\\d+)_(\\d+)\\.html\\?" +
                    "start=(\\d{4}-\\d{2}-\\d{2})&end=(\\d{4}-\\d{2}-\\d{2}).*");


    public static Request createRequest(PageInfo pageInfo) {
        String url = String.format(url_template, pageInfo.getCode(),
                pageInfo.getCurrentPage(), pageInfo.getStart(), pageInfo.getEnd());

        Request request = new Request(url);
        setPageInfo(request, pageInfo);
        setSupportClass(request, NtesPageProcessor.class);
        return request;
    }

    public static Request createRequest(String url) {
        Matcher matcher = url_pattern.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalUrlException(url);
        }

        String code = matcher.group(1);
        String pageNumStr = matcher.group(2);
        String start = matcher.group(3);
        String end = matcher.group(4);
        PageInfo pageInfo = new PageInfo(code, start, end,
                Integer.valueOf(pageNumStr), false);
        return createRequest(pageInfo);
    }

    static boolean hasNextPage(int size) {
        return size >= 60;
    }

    static PageInfo nextPage(PageInfo current) {
        return new PageInfo(current.getCode(),
                current.getStart(), current.getEnd(),
                current.getCurrentPage() + 1,
                current.isMmf());
    }

    static List<BaseNav> parsePage(Page page) {
        PageInfo pageInfo = getPageInfo(page.getRequest());
        String trCss = "#fn_fund_value_trend > table > tbody > tr";
        List<Selectable> trs = page.getHtml().css(trCss).nodes();
        List<BaseNav> result = new ArrayList<>(trs.size());
        trs.forEach(tr -> {
            BaseNav bean = parseRow(tr);
            bean.setCode(pageInfo.getCode());
            bean.setSource(NavSource.ntes);
            result.add(bean);
        });
        return result;
    }

    private static BaseNav parseRow(Selectable row) {
        List<String> fields = row.css("td", "text").all();
        String date = fields.get(0);
        String unitNavStr = fields.get(1);
        String accumNavStr = fields.get(2);
        NavNMF bean = new NavNMF();
        bean.setDate(DateUtil.parseDate(date));
        bean.setUnitNav(new BigDecimal(unitNavStr.trim()));
        bean.setAccumNav(new BigDecimal(accumNavStr.trim()));
        return bean;
    }


}
