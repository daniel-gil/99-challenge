package example.micronaut.providers.ninetynine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

public class NinetyNineCompany {

    public NinetyNineCompany() {}

    public NinetyNineCompany(@NotNull Long id, @NotNull String name, @NotNull String ric, Float shareprice) {
        this.id = id;
        this.name = name;
        this.ric = ric;
        this.sharePrice = shareprice;
    }

    @Id
    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "ric", nullable = false, unique = true)
    private String ric;

    Float sharePrice;

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

    public Float getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Float shareprice) {
        this.sharePrice = shareprice;
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
        sb.append("', sharePrice='");
        sb.append(sharePrice);
        sb.append("'}");
        return sb.toString();
    }
}
