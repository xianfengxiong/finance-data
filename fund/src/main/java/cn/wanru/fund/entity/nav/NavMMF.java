package cn.wanru.fund.entity.nav;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 货币基金净值
 * @author xxf
 * @date 17-9-9
 */
@Entity
@Table(name="nav_mmf")
public class NavMMF extends BaseNav {

    private BigDecimal yield7Days;

    private BigDecimal yield10k;

    // region Getter/Setter

    public BigDecimal getYield7Days() {
        return yield7Days;
    }

    public void setYield7Days(BigDecimal yield7Days) {
        this.yield7Days = yield7Days;
    }

    public BigDecimal getYield10k() {
        return yield10k;
    }

    public void setYield10k(BigDecimal yield10k) {
        this.yield10k = yield10k;
    }


    // endregion

    @Override
    public String toString() {
        return "NavMMF{" +
                "id=" + getId() +
                ", code='" + getCode() +
                ", date=" + getDate() +
                ", source=" + getSource() +
                ", yield7Days=" + yield7Days +
                ", yield10k=" + yield10k +
                '}';
    }
}
