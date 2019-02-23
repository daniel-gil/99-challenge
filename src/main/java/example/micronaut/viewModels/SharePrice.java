package example.micronaut.viewModels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SharePrice
{
    Long timestamp;
    
    Float price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Date datetime;

    String description;

    public SharePrice(){}

    public SharePrice(Date datetime, Float price, String description){
        this.datetime = datetime;
        this.price = price;
        this.description = description;
        this.timestamp = datetime.getTime();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}