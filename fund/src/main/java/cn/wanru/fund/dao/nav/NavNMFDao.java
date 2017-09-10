package cn.wanru.fund.dao.nav;

import cn.wanru.fund.entity.nav.NavNMF;
import cn.wanru.fund.entity.nav.NavSource;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @date 17-9-9
 */
public interface NavNMFDao extends JpaRepository<NavNMF,Long> {

    NavNMF findByCodeAndDateAndSource(String code, LocalDate date, NavSource source);

}
