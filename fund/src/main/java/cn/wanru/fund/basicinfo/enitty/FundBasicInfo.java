package cn.wanru.fund.basicinfo.enitty;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * @author xxf
 * @date 17-9-10
 */
@Entity
@Table(name = "fund_basic_info")
public class FundBasicInfo {

    @Id @GeneratedValue
    private Long id;

    private String code;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate foundDate;

    private Boolean mmf;

    // region Getter/Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(LocalDate foundDate) {
        this.foundDate = foundDate;
    }

    public Boolean getMmf() {
        return mmf;
    }

    public void setMmf(Boolean mmf) {
        this.mmf = mmf;
    }


    // endregion


    @Override
    public String toString() {
        return "FundBasicInfo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", foundDate=" + foundDate +
                ", mmf=" + mmf +
                '}';
    }
}
