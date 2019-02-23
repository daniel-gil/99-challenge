package example.micronaut.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "price")
public class Price {

    public Price() {}

    public Price(@NotNull Float shareprice, @NotNull Date datetime, Company company) {
        this.shareprice = shareprice;
        this.company = company;
        this.datetime = datetime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "shareprice", nullable = false)
    private Float shareprice;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private Date datetime;

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getShareprice() {
        return shareprice;
    }

    public void setShareprice(Float shareprice) {
        this.shareprice = shareprice;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book{");
        sb.append("id=");
        sb.append(id);
        sb.append(", company='");
        sb.append(company);
        sb.append("', shareprice='");
        sb.append(shareprice);
        sb.append("', datetime='");
        sb.append(datetime);
        sb.append("'}");
        return sb.toString();
    }
}
