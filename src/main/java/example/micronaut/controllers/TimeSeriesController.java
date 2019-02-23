package example.micronaut.controllers;

import example.micronaut.services.PriceService;
import example.micronaut.viewModels.TimeSeries;
import example.micronaut.viewModels.TimeSeries.TimeSeriesTypes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;


@Validated 
@Controller("/companies") 
public class TimeSeriesController {

    protected final PriceService priceService;

    public TimeSeriesController(PriceService priceService) { 
        this.priceService = priceService;
    }
  
    //////////// HOURLY ////////////
    @Get("/{id}/hourly") 
    TimeSeries timeSeriesHourly(Long id) { 
        return priceService.getTimeSeries(id, TimeSeriesTypes.HOURLY, today(), now());
    }

    @Get("/{id}/hourly/lastWeek") 
    TimeSeries timeSeriesHourlyLastWeek(Long id) { 
        Date lastWeek = addDays(today(), -7);
        return priceService.getTimeSeries(id, TimeSeriesTypes.HOURLY, lastWeek, now());
    }

    @Get("/{id}/hourly/{start}") 
    TimeSeries timeSeriesHourly(Long id, String start) { 
        Date startDate = parseStartDate(start);
        return priceService.getTimeSeries(id, TimeSeriesTypes.HOURLY, startDate, now());
    }
    
    @Get("/{id}/hourly/{start}/{end}") 
    TimeSeries timeSeriesHourly(Long id, String start, String end) { 
        Date startDate = parseStartDate(start);
        Date endDate = parseEndDate(end);
        return priceService.getTimeSeries(id, TimeSeriesTypes.HOURLY, startDate, endDate);
    }


    //////////// DAILY ////////////
    @Get("/{id}/daily/") 
    TimeSeries timeSeriesDaily(Long id) { 
        return priceService.getTimeSeries(id, TimeSeriesTypes.DAILY, today(), now());
    }
   
    @Get("/{id}/daily/lastWeek") 
    TimeSeries timeSeriesDailyLastWeek(Long id) { 
        Date lastWeek = addDays(today(), -7);
        return priceService.getTimeSeries(id, TimeSeriesTypes.DAILY, lastWeek, now());
    }

    @Get("/{id}/daily/{start}") 
    TimeSeries timeSeriesDaily(Long id, String start) { 
        Date startDate = parseStartDate(start);
        return priceService.getTimeSeries(id, TimeSeriesTypes.DAILY, startDate, now());
    }

    @Get("/{id}/daily/{start}/{end}") 
    TimeSeries timeSeriesDaily(Long id, String start, String end) { 
        Date startDate = parseStartDate(start);
        Date endDate = parseEndDate(end);
        return priceService.getTimeSeries(id, TimeSeriesTypes.DAILY, startDate, endDate);
    }


    //////////// WEEKLY ////////////
    @Get("/{id}/weekly") 
    TimeSeries timeSeriesWeekly(Long id) { 
        return priceService.getTimeSeries(id, TimeSeriesTypes.WEEKLY, today(), now());
    }

    @Get("/{id}/weekly/lastWeek") 
    TimeSeries timeSeriesWeeklyLastWeek(Long id) { 
        Date lastWeek = addDays(today(), -7);
        return priceService.getTimeSeries(id, TimeSeriesTypes.WEEKLY, lastWeek, now());
    }

    @Get("/{id}/weekly/{start}") 
    TimeSeries timeSeriesWeekly(Long id, String start) { 
        Date startDate = parseStartDate(start);
        return priceService.getTimeSeries(id, TimeSeriesTypes.WEEKLY, startDate, now());
    }

    @Get("/{id}/weekly/{start}/{end}") 
    TimeSeries timeSeriesWeekly(Long id, String start, String end) { 
        Date startDate = parseStartDate(start);
        Date endDate = parseEndDate(end);
        return priceService.getTimeSeries(id, TimeSeriesTypes.WEEKLY, startDate, endDate);
    }


    //////////// helpers ////////////
    private Date parseStartDate(String start){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate =  dateFormat.parse(start);
        } catch (Exception ex){
            System.out.printf("Exception=[%s]", ex.getMessage());
            return null;
        }
        return startDate;
    }

    private Date parseEndDate(String end){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate;
        try {
            // add 1 day to the endDate because the postgresql date 'between' condition is not inclusive
            endDate = addDays(dateFormat.parse(end), 1);
        } catch (Exception ex){
            System.out.printf("Exception=[%s]", ex.getMessage());
            return null;
        }
        return endDate;
    }

    private Date addDays(Date d, int numDays){
        Calendar c = Calendar.getInstance(); 
        c.setTime(d); 
        c.add(Calendar.DATE, numDays);
        return c.getTime();
    }

    private Date now(){
        return new Date();
    }

    private Date today(){
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);

        return today;
    }
}
