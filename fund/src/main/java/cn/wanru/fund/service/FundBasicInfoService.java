package cn.wanru.fund.service;

import cn.wanru.fund.dao.FundBasicInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxf
 * @date 17-9-10
 */
@Service
public class FundBasicInfoService {

    @Autowired
    private FundBasicInfoDao fundBasicInfoDao;

    public FundBasicInfoDao findByCode(String code) {
        return fundBasicInfoDao.findByCode(code);
    }

}
