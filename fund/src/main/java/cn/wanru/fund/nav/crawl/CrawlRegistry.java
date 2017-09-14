package cn.wanru.fund.nav.crawl;

import cn.wanru.fund.basicinfo.enitty.FundBasicInfo;
import cn.wanru.fund.basicinfo.service.FundBasicInfoService;
import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.fund.nav.crawl.eastmoney.EMUtil;
import cn.wanru.fund.nav.crawl.ntes.NTESUtil;
import cn.wanru.fund.nav.crawl.sina.SINAUtil;
import cn.wanru.fund.nav.crawl.tencent.TencentPageable;
import cn.wanru.fund.nav.crawl.tencent.TencentUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.Collections;
import java.util.List;

/**
 * @author xxf
 * @since 2017/9/13
 */
@Component
public class CrawlRegistry {

    @Autowired
    private Spider spider;

    @Autowired
    private FundBasicInfoService fundBasicInfoService;


    public void addEMRequest(String code, String start, String end) {
        List<FundBasicInfo> basicInfoList = getBasicInfoList(code);
        for (FundBasicInfo basicInfo : basicInfoList) {
            GenericPageable pageable =
                    EMUtil.createPageable(basicInfo.getCode(),
                            basicInfo.getMmf(), start, end);

            spider.addRequest(EMUtil.createRequest(pageable));
        }
    }

    public void addNTESRequest(String code, String start, String end) {
        List<FundBasicInfo> basicInfoList = getBasicInfoList(code);
        for (FundBasicInfo info : basicInfoList) {
            PageSizePageable pageable = NTESUtil.createPageable(info.getCode(),
                    info.getMmf(), start, end);
            spider.addRequest(NTESUtil.createRequest(pageable));
        }
    }

    public void addSINARequest(String code, String start, String end) {
        List<FundBasicInfo> basicInfoList = getBasicInfoList(code);
        for (FundBasicInfo info : basicInfoList) {
            PageSizePageable pageable =
                    SINAUtil.createPageable(info.getCode(),
                            info.getMmf(), start, end);
            spider.addRequest(SINAUtil.createRequest(pageable));
        }
    }

    public void addTencentRequest(String code, boolean yield7Days) {
        List<FundBasicInfo> basicInfoList = getBasicInfoList(code);
        for (FundBasicInfo info : basicInfoList) {
            TencentPageable pageable =
                    TencentUtil.createPageable(info.getCode(), info.getMmf(), yield7Days);
            spider.addRequest(TencentUtil.createRequest(pageable));
        }
    }

    private List<FundBasicInfo> getBasicInfoList(String code) {
        if (StringUtils.isEmpty(code)) {
            return fundBasicInfoService.findAll();
        } else {
            FundBasicInfo info = fundBasicInfoService.findByCode(code);
            return Collections.singletonList(info);
        }
    }

}
