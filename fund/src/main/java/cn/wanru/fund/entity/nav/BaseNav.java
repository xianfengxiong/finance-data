package cn.wanru.fund.entity.nav;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author xxf
 * @date 17-9-9
 */
public abstract class BaseNav {

    @Id @GeneratedValue
    private Long id;

    private String code;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private NavSource source;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public NavSource getSource() {
        return source;
    }

    public void setSource(NavSource source) {
        this.source = source;
    }
}
