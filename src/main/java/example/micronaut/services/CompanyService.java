package example.micronaut.services;

import javax.inject.Singleton;

import com.googlecode.jmapper.JMapper;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import example.micronaut.providers.DataProvider;
import example.micronaut.repositories.CompanyRepository;
import example.micronaut.SortingAndOrderArguments;
import example.micronaut.domain.Company;
import example.micronaut.viewModels.CompanyDetails;
import example.micronaut.viewModels.CompanyItem;

@Singleton 
public class CompanyService {
    protected final DataProvider dataProvider;
    protected final CompanyRepository companyRepository;

    public CompanyService(DataProvider dataProvider, CompanyRepository companyRepository){
        this.dataProvider = dataProvider;
        this.companyRepository = companyRepository;
    }
    

    public List<CompanyItem> getCompanies(@NotNull SortingAndOrderArguments args) { 
        // retrieve all the companies from our bbdd
        List<Company> companies = companyRepository.findAll(args);
        JMapper<CompanyItem, Company> userMapper= new JMapper<>(CompanyItem.class, Company.class);

        // apply the conversion
        List<CompanyItem> companyItems = new ArrayList<CompanyItem>();
        for (Company com : companies) {
            CompanyItem comItem = userMapper.getDestination(com);
            companyItems.add(comItem);
        }
        return companyItems;
    }

    public CompanyDetails getCompanyDetails(Long id) { 
        // check if the id belongs to an existing company
        Optional<Company> c = companyRepository.findById(id);

        if (c.isPresent()){
            JMapper<CompanyDetails, Company> userMapper= new JMapper<>(CompanyDetails.class, Company.class);
            CompanyDetails companyDetails = userMapper.getDestination(c.get());
            return companyDetails; 
        }
        return null;
    }

}