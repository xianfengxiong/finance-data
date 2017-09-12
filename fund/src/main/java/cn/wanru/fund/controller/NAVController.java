package cn.wanru.fund.controller;

import cn.wanru.fund.basicinfo.enitty.FundBasicInfo;
import cn.wanru.fund.basicinfo.service.FundBasicInfoService;
import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.fund.nav.crawl.eastmoney.EMUtil;
import cn.wanru.fund.nav.crawl.ntes.NTESUtil;
import cn.wanru.fund.nav.crawl.sina.SINAUtil;
import cn.wanru.fund.util.Code;
import cn.wanru.fund.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.Collections;
import java.util.List;

/**
 * @author xxf
 * @since 2017/9/11
 */
@RestController
@RequestMapping("/fund/nav")
public class NAVController {

    @Autowired
    private Spider spider;

    @Autowired
    private FundBasicInfoService basicInfoService;


    @RequestMapping("/{code}/{source}/{start}-{end}")
    public JsonResponse addRequest(
            @PathVariable(name="code",required = false) String code,
            @PathVariable(name="source") String source,
            @PathVariable(name="start") String start,
            @PathVariable(name="end") String end) {

        List<FundBasicInfo> toUse = null;
        if (code != null) {
            FundBasicInfo basicInfo = basicInfoService.findByCode(code);
            if (basicInfo == null) {
                return new JsonResponse<Void>(Code.not_fund,"code=" + code);
            }
            toUse = Collections.singletonList(basicInfo);
        }else{
            toUse = basicInfoService.findAll();
        }


        switch (source) {
            case "em" :
                addEMRequest(toUse,start,end);
                break;
            case "sina":
                addSINARequest(toUse,start,end);
                break;
            case "ntes" :
                addNTESRequest(toUse,start,end);
                break;
            default:
                return new JsonResponse(
                        Code.not_fund,"unknown source [“+source+”]");
        }

        return new JsonResponse(Code.ok,"ok");
    }

    private void addEMRequest(List<FundBasicInfo> basicInfoList,
                              String start,String end) {
        for (FundBasicInfo basicInfo : basicInfoList) {
            GenericPageable pageable =
                    EMUtil.createPageable(basicInfo.getCode(),
                    basicInfo.getMmf(),start,end);

            spider.addRequest(EMUtil.createRequest(pageable));
        }
    }

    private void addSINARequest(List<FundBasicInfo> basicInfoList,
                                String start,String end) {
        for (FundBasicInfo info : basicInfoList) {
            PageSizePageable pageable =
                    SINAUtil.createPageable(info.getCode(),
                    info.getMmf(),start,end);
            spider.addRequest(SINAUtil.createRequest(pageable));
        }
    }

    private void addNTESRequest(List<FundBasicInfo> basicInfoList,
                                String start,String end) {
        for (FundBasicInfo info : basicInfoList) {
            PageSizePageable pageable = NTESUtil.createPageable(info.getCode(),
                    info.getMmf(),start,end);
            spider.addRequest(NTESUtil.createRequest(pageable));
        }
    }

}
