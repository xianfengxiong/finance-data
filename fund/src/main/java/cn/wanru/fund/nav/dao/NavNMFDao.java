package cn.wanru.fund.nav.dao;

import cn.wanru.fund.nav.entity.NavNMF;
import cn.wanru.fund.nav.entity.NavSource;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @date 17-9-9
 */
public interface NavNMFDao extends JpaRepository<NavNMF,Long> {

    NavNMF findByCodeAndDateAndSource(String code, LocalDate date, NavSource source);

}
