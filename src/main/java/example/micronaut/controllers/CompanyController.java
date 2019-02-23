package example.micronaut.controllers;

import example.micronaut.services.CompanyService;
import example.micronaut.SortingAndOrderArguments;
import example.micronaut.viewModels.CompanyDetails;
import example.micronaut.viewModels.CompanyItem;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;

import java.util.List;
import javax.validation.Valid;


@Validated 
@Controller("/companies") 
public class CompanyController {
    protected final CompanyService companyService;

    public CompanyController(CompanyService companyService) { 
        this.companyService = companyService;
    }

    @Get("/{?args*}") 
    List<CompanyItem> companies(@Valid SortingAndOrderArguments args) { 
        return companyService.getCompanies(args);
    }
  
    @Get("/{id}") 
    CompanyDetails companyDetails(Long id) { 
        return companyService.getCompanyDetails(id);
    }
}
