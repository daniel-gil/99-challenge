package example.micronaut.viewModels;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class CompanyDetails {
    Long id;
    String name;
    String ric;
    String description;
    String country;
    Float shareprice;

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

}