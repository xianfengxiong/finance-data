package cn.wanru.fund.nav.crawl.tencent;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.entity.NavNMF;
import cn.wanru.fund.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * /*
 * fundNavAllYearData={"code": "jj000001",
 * "data": [["20011221", "1.0000", "1.0000"], ["20011228", "1.0000", "1.0000"],...]
 * "name": "\u534e\u590f\u6210\u957f\u6df7\u5408"}
 */
 /*
 * @author xxf
 * @since 2017/9/14
 */
public class TencentResult extends HashMap {

    public String getCode() {
        return (String) get("code");
    }

    @SuppressWarnings("unchecked")
    private List<List<String>> getData() {
        return (List<List<String>>) get("data");
    }

    public String getShortName() {
        return (String) get("name");
    }

    public List<BaseNav> toBaseNavList(TencentPageable pageable) {
        List<List<String>> data = getData();
        List<BaseNav> result = new ArrayList<>(data.size());
        for (List<String> entity : data) {
            result.add(pageable.isMmf()
                    ? parseMMF(entity,pageable.isYield7Days())
                    :  parseNMF(entity));
        }
        return result;
    }

    private BaseNav parseNMF(List<String> fields) {
        String date = fields.get(0);
        String unitNav = fields.get(1);
        String accumNav = fields.get(2);
        NavNMF bean = new NavNMF();
        bean.setDate(DateUtil.parseShortDate(date));
        bean.setUnitNav(new BigDecimal(unitNav));
        bean.setAccumNav(new BigDecimal(accumNav));
        return bean;
    }

    private BaseNav parseMMF(List<String> fields,boolean yield7Days) {
        NavMMF bean = new NavMMF();
        String date = fields.get(0);
        String value = fields.get(1);
        bean.setDate(DateUtil.parseShortDate(date));
        if (yield7Days) {
            bean.setYield7Days(new BigDecimal(value));
        } else {
            bean.setYield10k(new BigDecimal(value));
        }
        return bean;
    }

}
