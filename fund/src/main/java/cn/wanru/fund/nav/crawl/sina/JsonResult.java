package cn.wanru.fund.nav.crawl.sina;

import cn.wanru.fund.nav.entity.BaseNav;
import cn.wanru.fund.nav.entity.NavMMF;
import cn.wanru.fund.nav.entity.NavNMF;
import cn.wanru.fund.util.DateUtil;
import cn.wanru.fund.util.JsonUtil;
import org.apache.commons.collections.ListUtils;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * {"result":{"status":{"code":0},"data":{"data":null,"total_num":"0"}}}
 * @author xxf
 * @since 2017/9/12
 */
public class JsonResult {

    private Result result;

    // region Getter/Setter
    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }
    // endregion


    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    static class Result {
        Status status;
        Data data;
        // region Getter/Setter
        public Status getStatus() {
            return status;
        }
        public void setStatus(Status status) {
            this.status = status;
        }
        public Data getData() {
            return data;
        }
        public void setData(Data data) {
            this.data = data;
        }
        // endregion
    }

    static class Status {
        int code;
        // region Getter/Setter
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        // endregion
    }

    static class Data {
        List<Record> data;
        int total_num;

        public List<Record> getData() {
            return data;
        }

        public void setData(List<Record> data) {
            this.data = data;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }
    }

    static class Record extends HashMap {

        LocalDate getDate() {
            String fbrq = (String) get("fbrq");
            return DateUtil.parseDateTime(fbrq).toLocalDate();
        }

        BigDecimal getUnitNav() {
            String jjjz = (String) get("jjjz");
            return jjjz == null ? null : new BigDecimal(jjjz);
        }

        BigDecimal getAccumNav() {
            String ljjz = (String) get("ljjz");
            return ljjz == null ? null : new BigDecimal(ljjz);
        }

        BigDecimal getYield7Days() {
            String nhsyl = (String) get("nhsyl");
            return nhsyl == null ? null : new BigDecimal(nhsyl);
        }

        BigDecimal getYield10k() {
            String dwsy = (String) get("dwsy");
            return dwsy == null ? null : new BigDecimal(dwsy);
        }
    }

    public boolean isSuccess() {
        return getResult().getStatus().getCode() == 0;
    }

    public int getTotalNum() {
        return getResult().getData().getTotal_num();
    }

    public List<BaseNav> toBaseNavList(boolean mmf) {
        List<Record> records = this.getResult().getData().getData();
        if (records == null || records.size() == 0) {
            return Collections.emptyList();
        }
        List<BaseNav> result = new ArrayList<>(records.size());
        for (Record record : records) {
            result.add(mapToBaseNav(record,mmf));
        }
        return result;
    }

    private BaseNav mapToBaseNav(Record record,boolean mmf) {
        if (mmf) {
            NavMMF bean = new NavMMF();
            bean.setDate(record.getDate());
            bean.setYield7Days(record.getYield7Days());
            bean.setYield10k(record.getYield10k());
            return bean;
        }else{
            NavNMF bean = new NavNMF();
            bean.setDate(record.getDate());
            bean.setUnitNav(record.getUnitNav());
            bean.setAccumNav(record.getAccumNav());
            return bean;
        }
    }

}
