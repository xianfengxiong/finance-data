package cn.wanru.fund.basicinfo.dao;

import cn.wanru.fund.basicinfo.enitty.FundBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @date 17-9-10
 */
public interface FundBasicInfoDao extends JpaRepository<FundBasicInfo,Long> {

    FundBasicInfo findByCode(String code);

    int countByCode(String code);
}
