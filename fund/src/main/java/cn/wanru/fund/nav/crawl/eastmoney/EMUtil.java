package cn.wanru.fund.nav.crawl.eastmoney;

import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.crawler.ParsePageException;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.entity.NavNMF;
import cn.wanru.fund.nav.entity.NavSource;
import cn.wanru.fund.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.wanru.webmagic.PageUtil.setPageable;
import static cn.wanru.webmagic.PageUtil.setSupportClass;

/**
 * @author xxf
 * @since 2017/9/12
 */
public class EMUtil {

    private static final String url_template = "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=%s&page=%s&per=%s&sdate=%s&edate=%s";

    private static final Pattern content_pattern = Pattern.compile("var apidata=\\{\\s*content:\"([^,]*)\",records:(\\d+),pages:(\\d+),curpage:(\\d+)\\};");

    private static final Pattern date_pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*");


    public static GenericPageable createPageable(
            String code, boolean mmf, String start, String end) {

        GenericPageable pageable = new GenericPageable();
        pageable.setCode(code);
        pageable.setMmf(mmf);
        pageable.setStart(start);
        pageable.setEnd(end);
        return pageable;
    }

    public static Request createRequest(GenericPageable pageable) {
        String url = String.format(url_template, pageable.getCode(),
                1,40000,pageable.getStart(),pageable.getEnd());
        Request request = new Request(url);
        setPageable(request,pageable);
        setSupportClass(request,EMUtil.class);
        return request;
    }

    public static List<BaseNav> parsePage(Page page, GenericPageable pageable) {
        String text = page.getRawText();
        Matcher matcher = content_pattern.matcher(text);
        if (!matcher.matches()) {
            throw new ParsePageException(text);
        }

        String content = matcher.group(1);
        int records = Integer.valueOf(matcher.group(2).trim());
        int pages = Integer.valueOf(matcher.group(3).trim());
        int curpage = Integer.valueOf(matcher.group(4).trim());

        if (records == 0) {
            return Collections.emptyList();
        }

        Assert.isTrue(pages == 1, "pages=" + pages);
        Assert.isTrue(curpage == 1, "curpage=" + curpage);

        List<BaseNav> list = parseContent(content,pageable);

        Assert.isTrue(list.size() == records, "records=" + records);

        return list;
    }

    private static List<BaseNav> parseContent(String content,GenericPageable pageable) {
        Document doc = Jsoup.parse(content);
        Element body = doc.body();
        Elements head = body.select("table thead tr th");
        Elements trs = body.select("table tbody tr");

        TableType type = parseHeader(head);

        List<BaseNav> result = new ArrayList<>(trs.size());
        for (Element tr : trs) {
            BaseNav baseNav = type.parse(tr);
            baseNav.setCode(pageable.getCode());
            result.add(baseNav);
        }

        return result;
    }

    private static TableType parseHeader(Elements head) {
        int column = head.size();
        String secondFieldName = head.get(1).text().trim();
        boolean mmf = !secondFieldName.startsWith("单位净值");
        if (!mmf && column == 7) {
            return TableType.nmf_6column;
        }
        if (mmf && column == 6) {
            return TableType.mmf_6column;
        }
        if (mmf && column == 7) {
            return TableType.mmf_7column;
        }
        throw new ParsePageException("未知的表格类型不能解析 : " + head.text());
    }

    private enum TableType {
        nmf_6column("净值日期,单位净值,累计净值,日增长率,申购状态,赎回状态,分红送配") {
            @Override
            BaseNav parse(Element row) {
                Elements fields = row.select("td");
                NavNMF bean = new NavNMF();
                bean.setSource(NavSource.east_money);
                bean.setDate(parseDate(fields.get(0).text()));
                bean.setUnitNav(parseNumber(fields.get(1).text()));
                bean.setAccumNav(parseNumber(fields.get(2).text()));
                return bean;
            }
        },
        mmf_6column("净值日期,每万份收益,7日年化收益率(%),申购状态,赎回状态,分红送配") {
            @Override
            BaseNav parse(Element row) {
                Elements fields = row.select("td");
                NavMMF bean = new NavMMF();
                bean.setSource(NavSource.east_money);
                bean.setDate(parseDate(fields.get(0).text()));
                bean.setYield10k(parseNumber(fields.get(1).text()));
                bean.setYield7Days(parsePercentNumber(fields.get(2).text()));
                return bean;
            }
        },
        mmf_7column("净值日期,每万份收益,7日年化收益率(%),最近运作期年化收益率,申购状态,赎回状态,分红送配") {
            @Override
            BaseNav parse(Element row) {
                return mmf_6column.parse(row);
            }
        };

        String header;

        abstract BaseNav parse(Element row);

        TableType(String header) {
            this.header = header;
        }

        public String getHeader() {
            return header;
        }
    }

    private static LocalDate parseDate(String date) {
        date = date.trim();
        if (StringUtils.isEmpty(date)) {
            return null;
        }

        Matcher matcher = date_pattern.matcher(date);
        if (!matcher.matches()) {
            throw new RuntimeException("非法的日期格式[" + date + "]");
        }

        date = matcher.group(1);
        return DateUtil.parseDate(date);
    }

    private static BigDecimal parseNumber(String str) {
        str = str.trim();
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    // 没有除以100
    private static BigDecimal parsePercentNumber(String str) {
        str = str.trim();
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        str = str.substring(0, str.length() - 1);
        return new BigDecimal(str);
    }
}
