package example.micronaut.jobs;

import io.micronaut.scheduling.annotation.Scheduled;
import javax.inject.Singleton;
import example.micronaut.providers.DataProvider;

@Singleton 
public class DataRefresherJob {
    protected final DataProvider dataProvider;

    public DataRefresherJob(DataProvider dataProvider){
        this.dataProvider = dataProvider;
    }
    
    @Scheduled(fixedDelay = "20s") 
    void executeCompaniesRefresh() {
        dataProvider.updateCompanyList();
    }

    @Scheduled(fixedDelay = "60s") 
    void executeCompanyDetailsRefresh() {
        dataProvider.updateCompanyDetails();
    }
}