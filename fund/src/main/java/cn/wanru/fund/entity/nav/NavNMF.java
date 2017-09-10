package cn.wanru.fund.entity.nav;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 非货币基金净值
 * @author xxf
 * @date 17-9-9
 */
@Entity
@Table(name="nav_nmf")
public class NavNMF extends BaseNav {

    private BigDecimal unitNav;

    private BigDecimal accumNav;

    // region Getter/Setter

    public BigDecimal getUnitNav() {
        return unitNav;
    }

    public void setUnitNav(BigDecimal unitNav) {
        this.unitNav = unitNav;
    }

    public BigDecimal getAccumNav() {
        return accumNav;
    }

    public void setAccumNav(BigDecimal accumNav) {
        this.accumNav = accumNav;
    }

    // endregion


    @Override
    public String toString() {
        return "NavNMF{" +
                "id=" + getId() +
                ", code='" + getCode() +
                ", date=" + getDate() +
                ", source=" + getSource() +
                ", unitNav=" + unitNav +
                ", accumNav=" + accumNav +
                '}';
    }
}
