package example.micronaut.viewModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeSeries {
    Long companyId;
    TimeSeriesTypes type;
    String name;
    String ric;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Date endDate;

    List<SharePrice> sharePrices;

    
    public TimeSeries (){ 
        this.sharePrices = new ArrayList<SharePrice>();
     }

    public TimeSeries (Long companyId, String name, String ric, TimeSeriesTypes type, Date startDate, Date endDate){ 
        this.companyId = companyId;
        this.type = type;
        this.name = name;
        this.ric = ric;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sharePrices = new ArrayList<SharePrice>();
    }

    public enum TimeSeriesTypes {
        HOURLY, DAILY, WEEKLY
    }
   
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public TimeSeriesTypes getType() {
        return type;
    }

    public void setType(TimeSeriesTypes type) {
        this.type = type;
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
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<SharePrice> getSharePrices(){
        return sharePrices;
    }

    public void addSharedPrice(SharePrice sharePrice){
        sharePrices.add(sharePrice);
    }
}