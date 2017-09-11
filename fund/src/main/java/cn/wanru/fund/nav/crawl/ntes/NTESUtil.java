package cn.wanru.fund.nav.crawl.ntes;

import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.crawler.ParsePageException;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.entity.NavNMF;
import cn.wanru.fund.nav.entity.NavSource;
import cn.wanru.fund.util.DateUtil;
import cn.wanru.webmagic.PageUtil;
import cn.wanru.webmagic.Pageable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Selectable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static cn.wanru.fund.crawler.Util.setSupportClass;

/**
 * @author xxf
 * @date 17-9-9
 */
public class NTESUtil {

    /*
     * "http://quotes.money.163.com/fund/jzzs_150240_0.html?start=2016-04-01&end=2016-11-11&sort=TDATE&order=desc"
     */
    private static final String nmf_url_template =
            "http://quotes.money.163.com/fund/jzzs_%s_%s.html?start=%s&end=%s&sort=TDATE&order=desc";

    /*
     * "http://quotes.money.163.com/fund/lssy_000009_0.html?start=2017-07-02&end=2017-09-08"
     */
    private static final String mmf_url_template =
            "http://quotes.money.163.com/fund/lssy_%s_%s.html?start=%s&end=%s";

    private static final Pattern url_pattern = Pattern.compile(
            "http://quotes\\.money\\.163\\.com/fund/jzzs_(\\d+)_(\\d+)\\.html\\?" +
                    "start=(\\d{4}-\\d{2}-\\d{2})&end=(\\d{4}-\\d{2}-\\d{2}).*");


    public static Request createRequest(GenericPageable pageable) {
        String url = null;
        if (pageable.isMmf()) {
            url = String.format(mmf_url_template, pageable.getCode(),
                    pageable.getCurrentPage(), pageable.getStart(), pageable.getEnd());
        } else {
            url = String.format(nmf_url_template, pageable.getCode(),
                    pageable.getCurrentPage(), pageable.getStart(), pageable.getEnd());
        }
        Request request = new Request(url);
        PageUtil.setPageable(request, pageable);
        setSupportClass(request, NTESPageProcessor.class);
        return request;
    }

    static List<BaseNav> parsePage(Page page, Pageable pageable) {
        GenericPageable genericPageable = (GenericPageable) pageable;
        String trCss = "#fn_fund_value_trend > table > tbody > tr";
        List<Selectable> trs = page.getHtml().css(trCss).nodes();
        List<BaseNav> result = new ArrayList<>(trs.size());
        trs.forEach(tr -> {
            BaseNav bean = parseRow(tr, genericPageable);
            bean.setCode(genericPageable.getCode());
            bean.setSource(NavSource.ntes);
            result.add(bean);
        });
        return result;
    }

    private static BaseNav parseRow(Selectable row,GenericPageable pageable) {
        return pageable.isMmf() ? parseMMFRow(row) : parseNMFRow(row) ;
    }

    private static NavMMF parseMMFRow(Selectable row) {
        List<String> fields = row.css("td", "text").all();
        String date = fields.get(0);
        String yield10k = fields.get(1).trim();
        String yield7days = fields.get(2).trim();

        if (!yield7days.endsWith("%")) {
            throw new ParsePageException("七日年化收益率字段不是以%结尾,fields=" +
                    Arrays.toString(fields.toArray()));
        }

        yield7days = yield7days.substring(0,yield7days.length()-1);

        NavMMF bean = new NavMMF();
        bean.setDate(DateUtil.parseDate(date));
        bean.setYield7Days(new BigDecimal(yield7days));
        bean.setYield10k(new BigDecimal(yield10k));
        return bean;
    }

    private static NavNMF parseNMFRow(Selectable row) {
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