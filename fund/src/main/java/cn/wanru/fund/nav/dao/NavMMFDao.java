package cn.wanru.fund.nav.dao;

import cn.wanru.fund.nav.entity.NavSource;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import cn.wanru.fund.nav.entity.NavMMF;
/**
 * @author xxf
 * @date 17-9-9
 */
public interface NavMMFDao extends JpaRepository<NavMMF,Long> {

    NavMMF findByCodeAndDateAndSource(String code, LocalDate date, NavSource source);
}
