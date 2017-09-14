package cn.wanru.fund.controller;

import cn.wanru.fund.basicinfo.service.FundBasicInfoService;
import cn.wanru.fund.nav.crawl.CrawlRegistry;
import cn.wanru.fund.util.Code;
import cn.wanru.fund.util.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxf
 * @since 2017/9/11
 */
@RestController
@RequestMapping("/fund/nav")
public class NAVController {

    @Autowired
    private CrawlRegistry crawlRegistry;

    @Autowired
    private FundBasicInfoService fundBasicInfoService;


    @RequestMapping("/{code}/{source}/{start}/{end}")
    public JsonResponse addRequest(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "source") String source,
            @PathVariable(name = "start") String start,
            @PathVariable(name = "end") String end) {

        if (StringUtils.isNotEmpty(code) && !fundBasicInfoService.existByCode(code)) {
            return new JsonResponse(Code.not_fund, "code [" + code + "] not found");
        }

        if (source.equals("tencent")) {
            crawlRegistry.addTencentRequest(code, true);
            crawlRegistry.addTencentRequest(code, true);
        }else {
            addRequestBySource(code, source, start, end);
        }
        return new JsonResponse(Code.ok, "ok");
    }

    @RequestMapping("/{source}/{start}/{end}")
    public JsonResponse addRequest(
            @PathVariable(name = "source") String source,
            @PathVariable(name = "start") String start,
            @PathVariable(name = "end") String end) {

        if (source.equals("tencent")) {
            crawlRegistry.addTencentRequest(null,true);
            crawlRegistry.addTencentRequest(null,true);
        }else{
            addRequestBySource(null,source,start,end);
        }

        return new JsonResponse(Code.ok, "ok");
    }

    private void addRequestBySource(String code,String source,
                                    String start,String end) {
        switch (source) {
            case "em":
                crawlRegistry.addEMRequest(code, start, end);
                break;
            case "sina":
                crawlRegistry.addSINARequest(code, start, end);
                break;
            case "ntes":
                crawlRegistry.addNTESRequest(code, start, end);
                break;
            default:
                throw new IllegalArgumentException(source);
        }
    }

}
