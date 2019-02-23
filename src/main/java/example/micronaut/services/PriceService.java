package example.micronaut.services;

import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import example.micronaut.providers.DataProvider;
import example.micronaut.repositories.CompanyRepository;
import example.micronaut.repositories.PriceRepository;
import example.micronaut.domain.Company;
import example.micronaut.domain.Price;
import example.micronaut.viewModels.SharePrice;
import example.micronaut.viewModels.TimeSeries;
import example.micronaut.viewModels.TimeSeries.TimeSeriesTypes;

@Singleton 
public class PriceService {
    protected final DataProvider dataProvider;
    protected final CompanyRepository companyRepository;
    protected final PriceRepository priceRepository;

    public PriceService(DataProvider dataProvider, CompanyRepository companyRepository, PriceRepository priceRepository){
        this.dataProvider = dataProvider;
        this.companyRepository = companyRepository;
        this.priceRepository = priceRepository;
    }

    public TimeSeries getTimeSeries(Long id, TimeSeriesTypes type, Date startDate, Date endDate){
        Optional<Company> optCom = companyRepository.findById(id);

        if (optCom.isPresent()){
            Company c = optCom.get();
            TimeSeries ts = new TimeSeries(id, c.getName(), c.getRic(), type, startDate, endDate);

            List<Price> prices = priceRepository.findAll(id, startDate, endDate);
            Price lastPrice = null;

            // loop backwards the prices list (to catch the first one that satisfy the type criteria)
            for (int j = prices.size() - 1; j >= 0; j--) {
                Price price = prices.get(j);
                PriceProcessed res = processPrice(type, lastPrice, price);
                if ( !res.skip ) {
                    SharePrice sp = new SharePrice(price.getDatetime(), price.getShareprice(), res.description);
                    ts.addSharedPrice(sp);
                }
                lastPrice = price;
            }
            return ts;
        }
        return null;
    }

    private class PriceProcessed {
        boolean skip;
        String description;
    }

    private PriceProcessed processPrice(TimeSeriesTypes type, Price lastPrice, Price price){
        PriceProcessed response = new PriceProcessed();
        Date priceDate = price.getDatetime();

        switch (type) {
            case HOURLY:
            case DAILY:
            {
                DateFormat dateFormat;
                String prefix;
                if (type == TimeSeriesTypes.HOURLY) {
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH'h'");
                    prefix = "Price for date/hour: ";
                } else{
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    prefix = "Price for date: ";
                } 

                String priceStrDate = dateFormat.format(priceDate);
                if (lastPrice == null) {
                    // the first item processed is always stored
                    response.skip = false;
                    response.description = prefix + priceStrDate;
                    return response;
                }

                Date lastPriceDate = lastPrice.getDatetime();
                String lastPriceStrDate = dateFormat.format(lastPriceDate);

                // just skip the current price if we have already processed
                if( !lastPriceStrDate.equals(priceStrDate) ){
                    response.skip = false;
                    response.description = prefix + priceStrDate;
                    return response;
                }
                break;
            }
            case WEEKLY:
            {
                Calendar cal = Calendar.getInstance();
                cal.setTime(priceDate);
                int priceWeek = cal.get(Calendar.WEEK_OF_YEAR);

                if (lastPrice == null) {
                    // the first item processed is always stored
                    response.skip = false;
                    response.description = "Price for week of year: " + String.valueOf(priceWeek);;
                    return response;
                }

                Date lastPriceDate = lastPrice.getDatetime();
                cal.setTime(lastPriceDate);
                int lastPriceWeek = cal.get(Calendar.WEEK_OF_YEAR);


                // just skip the current price if we have already processed, for comparison we use the week of the year
                if (lastPriceWeek != priceWeek) {
                    response.skip = false;
                    response.description = "Price for week of year: " + String.valueOf(priceWeek);
                    return response;
                }
                break;
            }
            default:
                break;
        }
        response.skip = true;
        return response;
    }
}