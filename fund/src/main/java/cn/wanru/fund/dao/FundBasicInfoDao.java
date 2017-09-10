package cn.wanru.fund.dao;

import cn.wanru.fund.entity.FundBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @date 17-9-10
 */
public interface FundBasicInfoDao extends JpaRepository<FundBasicInfo,Long> {

    FundBasicInfoDao findByCode(String code);

}
