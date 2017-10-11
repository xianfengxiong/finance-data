package cn.wanru.fund.nav.crawl.sina;

import cn.wanru.fund.crawler.ParsePageException;
import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.entity.NavNMF;
import cn.wanru.fund.util.DateUtil;
import cn.wanru.fund.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xxf
 * @since 17/3/29
 */
class JsonResult {

  private String json;

  private Map result;

  private Map status;

  private int code;

  private Map data;

  private Object val;

  private int totalNum;

  JsonResult(String json) {
    this.json = json;

    //{"result":{"status":{"msg":"APPCYCLE (errno: 2)","code":11},"data":[]}}
    Map map = JsonUtil.fromJson(json,Map.class);
    result = (Map) map.get("result");
    status = (Map) result.get("status");
    code = (int) status.get("code");
    data = (Map) result.get("data");
    val = data.get("data");
    totalNum = Integer.valueOf((String) data.get("total_num"));
  }

  boolean isSuccess() {
    return code == 0;
  }

  int getStatusCode() {
    return code;
  }

  int getTotalNum() {
    return totalNum;
  }

  int getTotalPage() {
    if (totalNum == 0 || val ==null){
      return 0;
    }

    return (int)Math.ceil(totalNum / 20.0);
  }

  @SuppressWarnings("unchecked")
  List<BaseNav> toBaseNavList(boolean mmf) {
    if (val == null) {
      return Collections.emptyList();
    }
    if (val instanceof Map) {
      // 货币型基金
      if (!mmf) {
        throw new ParsePageException("json result " + toString()
        + " is not a mmf fund format");
      }
      return parseMapData((Map)val);
    }else{
      // 非货币型或者货币型都有可能
      return parseListData((List)val,mmf);
    }
  }

  private List<BaseNav> parseMapData(Map<String,Map<String,String>> map){
    if (map.size() <= 0) {
      return Collections.emptyList();
    }

    List<BaseNav> beans = new ArrayList<>(map.size());
    map.values().forEach(m->{
      String date = m.get("fbrq");
      String nh = m.get("nhsyl");
      String dw = m.get("dwsy");

      NavMMF bean = new NavMMF();
      bean.setDate(DateUtil.parseDateTime(date).toLocalDate());
      BigDecimal nhD = StringUtils.isEmpty(nh) ? null : new BigDecimal(nh);
      BigDecimal dwD = StringUtils.isEmpty(dw) ? null :  new BigDecimal(dw);
      bean.setYield7Days(nhD);
      bean.setYield10k(dwD);
      beans.add(bean);
    });

    return beans;
  }

  private List<BaseNav> parseListData(List<Map<String,String>> list,boolean mmf){
    if (list.size() <= 0){
      return Collections.emptyList();
    }

    List<BaseNav> result = new ArrayList<>(list.size());
    list.forEach(map->result.add(populateBean(map,mmf)));

    return result;
  }

  private BaseNav populateBean(Map<String,String> map,boolean mmf) {
    String dateStr = map.get("fbrq");
    LocalDate date = DateUtil.parseDateTime(dateStr).toLocalDate();

    if (mmf) {
      NavMMF bean = new NavMMF();
      bean.setDate(date);
      String nhsyl = map.get("nhsyl");
      String dwsy = map.get("dwsy");
      BigDecimal nhD = StringUtils.isEmpty(nhsyl) ? null : new BigDecimal(nhsyl);
      BigDecimal dwD = StringUtils.isEmpty(dwsy) ? null : new BigDecimal(dwsy);
      bean.setYield7Days(nhD);
      bean.setYield10k(dwD);
      return bean;
    }else{
      NavNMF bean = new NavNMF();
      bean.setDate(date);
      String jjjz = map.get("jjjz");
      String ljjz = map.get("ljjz");
      BigDecimal jjjzD = StringUtils.isEmpty(jjjz) ? null : new BigDecimal(jjjz);
      BigDecimal ljjzD = StringUtils.isEmpty(ljjz) ? null : new BigDecimal(ljjz);
      bean.setUnitNav(jjjzD);
      bean.setAccumNav(ljjzD);
      return bean;
    }

  }

  @Override
  public String toString() {
    return json;
  }
}
