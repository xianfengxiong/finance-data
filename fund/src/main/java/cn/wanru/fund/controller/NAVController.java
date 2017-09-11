package cn.wanru.fund.controller;

import cn.wanru.fund.basicinfo.enitty.FundBasicInfo;
import cn.wanru.fund.basicinfo.service.FundBasicInfoService;
import cn.wanru.fund.crawler.PageSizePageable;
import cn.wanru.fund.nav.crawl.ntes.NTESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

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

    @RequestMapping("/ntes")
    public String addRequest(String code, String start, String end) {
        spider.addRequest(createNTESRequests(code, start, end));
        return "add requests success";
    }

    private Request createNTESRequests(String code, String start, String end) {
        FundBasicInfo basicInfo = basicInfoService.findByCode(code);
        PageSizePageable pageable = new PageSizePageable(60);
        pageable.setCode(basicInfo.getCode());
        pageable.setCurrentPage(0);
        pageable.setCurrentPageSize(0);
        pageable.setMmf(basicInfo.getMmf());
        pageable.setStart(start);
        pageable.setEnd(end);
        return NTESUtil.createRequest(pageable);
    }
}
