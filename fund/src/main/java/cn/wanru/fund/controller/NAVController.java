package cn.wanru.fund.controller;

import cn.wanru.fund.basicinfo.enitty.FundBasicInfo;
import cn.wanru.fund.basicinfo.service.FundBasicInfoService;
import cn.wanru.fund.crawler.GenericPageable;
import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.fund.nav.crawl.eastmoney.EMUtil;
import cn.wanru.fund.nav.crawl.ntes.NTESUtil;
import cn.wanru.fund.nav.crawl.sina.SINAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.Arrays;
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


    @RequestMapping("/{source}/{start}/{end}/{code}")
    public String addRequest(@PathVariable(name="source") String source,
                             @PathVariable(name="start") String start,
                             @PathVariable(name="end") String end,
                             @PathVariable(name="code") String code) {

        List<FundBasicInfo> toUse = null;
        if (code != null) {
            FundBasicInfo basicInfo = basicInfoService.findByCode(code);
            toUse = Arrays.asList(basicInfo);
        }else{
            toUse = basicInfoService.findAll();
        }

    }


    @RequestMapping("/ntes")
    public String addNTESRequest(String code, String start, String end) {
        FundBasicInfo basicInfo = basicInfoService.findByCode(code);
        PageSizePageable pageable = NTESUtil.createPageable(basicInfo.getCode(),
                basicInfo.getMmf(),start,end);
        spider.addRequest(NTESUtil.createRequest(pageable));
        return "success";
    }

    @RequestMapping("/ntes/all")
    public String addAllNTESRequest(String start, String end) {
        List<FundBasicInfo> infos = basicInfoService.findAll();
        for (FundBasicInfo info : infos) {
            PageSizePageable pageable = NTESUtil.createPageable(info.getCode(),
                    info.getMmf(),start,end);
            spider.addRequest(NTESUtil.createRequest(pageable));
        }
        return "success";
    }

    @RequestMapping("/sina")
    public String addSINARequest(String code, String start, String end) {
        FundBasicInfo basicInfo = basicInfoService.findByCode(code);
        PageSizePageable pageable = SINAUtil.createPageable(basicInfo.getCode(),
                basicInfo.getMmf(),start,end);
        spider.addRequest(SINAUtil.createRequest(pageable));
        return "success";
    }

    @RequestMapping("/sina/all")
    public String addAllSINARequest(String start, String end) {
        List<FundBasicInfo> infos = basicInfoService.findAll();
        for (FundBasicInfo info : infos) {
            PageSizePageable pageable = SINAUtil.createPageable(info.getCode(),
                    info.getMmf(),start,end);
            spider.addRequest(SINAUtil.createRequest(pageable));
        }
        return "success";
    }

    @RequestMapping("/em")
    public String addEMRequest(String code, String start, String end) {
        FundBasicInfo basicInfo = basicInfoService.findByCode(code);
        GenericPageable pageable = EMUtil.createPageable(basicInfo.getCode(),
                basicInfo.getMmf(),start,end);
        spider.addRequest(EMUtil.createRequest(pageable));
        return "success";
    }

    @RequestMapping("/em/all")
    public String addAllEMRequest(String start, String end) {
        List<FundBasicInfo> infos = basicInfoService.findAll();
        for (FundBasicInfo info : infos) {
            PageSizePageable pageable = NTESUtil.createPageable(info.getCode(),
                    info.getMmf(),start,end);
            spider.addRequest(NTESUtil.createRequest(pageable));
        }
        return "success";
    }
}
