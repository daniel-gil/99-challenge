package example.micronaut.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.jmapper.annotations.JMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
public class Company {

    public Company() {}

    public Company(@NotNull Long id, @NotNull String name, @NotNull String ric, Float shareprice, String description, String country) {
        this.id = id;
        this.name = name;
        this.ric = ric;
        this.description = description;
        this.country = country;
        this.shareprice = shareprice;
    }

    @Id
    @NotNull
    @JMap
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JMap
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @JMap
    @Column(name = "ric", nullable = false, unique = true)
    private String ric;

    @Column(name = "description", nullable = true, unique = false)
    private String description;

    @Column(name = "country", nullable = true, unique = false)
    private String country;

    @JMap("sharePrice")
    Float shareprice;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private Set<Price> prices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getShareprice() {
        return shareprice;
    }

    public void setShareprice(Float shareprice) {
        this.shareprice = shareprice;
    }
   
    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Company{");
        sb.append("id=");
        sb.append(id);
        sb.append(", ric='");
        sb.append(ric);
        sb.append("', name='");
        sb.append(name);
        sb.append("', shareprice='");
        sb.append(shareprice);
        sb.append("', description='");
        sb.append(description);
        sb.append("', country='");
        sb.append(country);
        sb.append("'}");
        return sb.toString();
    }
}
