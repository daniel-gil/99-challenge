package example.micronaut.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.micronaut.repositories.CompanyRepository;
import example.micronaut.repositories.PriceRepository;
import example.micronaut.domain.Company;
import example.micronaut.providers.ninetynine.NinetyNineClient;
import example.micronaut.providers.ninetynine.models.NinetyNineCompany;
import example.micronaut.providers.ninetynine.models.NinetyNineCompanyInfo;

import javax.inject.Singleton;

import com.googlecode.jmapper.JMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.reactivex.Maybe;


@Singleton
public class DataProvider {
    private static final Logger LOG = LoggerFactory.getLogger(DataProvider.class); 

    private final NinetyNineClient client;
    protected final CompanyRepository companyRepository;
    protected final PriceRepository priceRepository;

    public DataProvider(NinetyNineClient client, CompanyRepository companyRepository, PriceRepository priceRepository) {
       this.client = client;
       this.companyRepository = companyRepository;
       this.priceRepository = priceRepository;
    }


    public void updateCompanyList() {
        Maybe<List<NinetyNineCompany>> companies = client.fetchCompanies();
        
        companies.subscribe(
            s->handleCompanyList(s),
            ex -> System.out.print("Error: " + ex.getMessage()), 
            () -> System.out.print("update companies action completed"));
    }


    private void handleCompanyList(List<NinetyNineCompany> list){
        for (NinetyNineCompany nnc : list) {

            // create a mapper for converting the NinetyNineCompany (provider domain) to our Company (domain)
            JMapper<Company, NinetyNineCompany> userMapper= new JMapper<>(Company.class, NinetyNineCompany.class);
            Company c = userMapper.getDestination(nnc);

            Optional<Company> optCompany = companyRepository.findById(c.getId());
            if (!optCompany.isPresent()) {
                companyRepository.save(c.getId(), c.getName(), c.getRic(), c.getShareprice(), null, null);
            } else {
                Company company = optCompany.get();
                companyRepository.update(c.getId(), c.getName(), c.getRic(), c.getShareprice(), company.getDescription(), company.getCountry()); 
                priceRepository.save(c.getShareprice(), new Date(), c);
            }
        }
    }


    public void updateCompanyDetails() {
        LOG.info("Fetching company details at {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        Maybe<List<NinetyNineCompany>> companies = client.fetchCompanies();

        companies.subscribe(
            s->fetchCompaniesDetails(s),
            ex -> System.out.print("Error: " + ex.getMessage()), 
            () -> System.out.print("update companies details action completed"));
    }

    private void fetchCompaniesDetails(List<NinetyNineCompany> list){
        for (NinetyNineCompany nnc : list) {

            // create a mapper for converting the NinetyNineCompany (provider domain) to our Company (domain)
            JMapper<Company, NinetyNineCompany> userMapper= new JMapper<>(Company.class, NinetyNineCompany.class);
            Company c = userMapper.getDestination(nnc);

            LOG.info("handleCompanyDetails ****: {} - {}", c.getId(), c.getName());
            Maybe<NinetyNineCompanyInfo> companyDetails = client.fetchCompanyDetails(c.getId());
            
            companyDetails.subscribe(
                cd->handleCompanyDetails(cd),
                ex -> System.out.print("Error: " + ex.getMessage()), 
                () -> System.out.print("update company details action completed"));
        }
    }

    private void handleCompanyDetails(NinetyNineCompanyInfo ci){
        Optional<Company> optCompany = companyRepository.findById(ci.getId());
        
        if (!optCompany.isPresent()) {
            companyRepository.save(ci.getId(), ci.getName(), ci.getRic(), ci.getSharePrice(), ci.getDescription(), ci.getCountry());
        } else {
            Company company = optCompany.get();
            
            // check if it's necessary to be updated
            if (company.getDescription() != ci.getDescription() || company.getCountry() != ci.getCountry()) {
                companyRepository.update(ci.getId(), ci.getName(), ci.getRic(), ci.getSharePrice(), ci.getDescription(), ci.getCountry());
            }
        }
        
    }
}