package cn.wanru.fund.basicinfo.service;

import cn.wanru.fund.basicinfo.dao.FundBasicInfoDao;
import cn.wanru.fund.basicinfo.enitty.FundBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxf
 * @date 17-9-10
 */
@Service
public class FundBasicInfoService {

    @Autowired
    private FundBasicInfoDao fundBasicInfoDao;

    public List<FundBasicInfo> findAll() {
        return fundBasicInfoDao.findAll();
    }

    public FundBasicInfo findByCode(String code) {
        return fundBasicInfoDao.findByCode(code);
    }

    public boolean existByCode(String code) {
        return fundBasicInfoDao.countByCode(code) > 0;
    }
}
