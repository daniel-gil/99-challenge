package example.micronaut.providers.ninetynine;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriTemplate;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import javax.inject.Singleton;

import example.micronaut.ApplicationConfiguration;
import example.micronaut.providers.ninetynine.models.NinetyNineCompany;
import example.micronaut.providers.ninetynine.models.NinetyNineCompanyInfo;

import java.util.List;

@Singleton 
public class NinetyNineClient {

    private final RxHttpClient httpClient;
    private ApplicationConfiguration configuration;


    public NinetyNineClient(@Client(ApplicationConfiguration.NINETYNINE_API_URL) RxHttpClient httpClient,  
                         ApplicationConfiguration configuration) {  
        this.httpClient = httpClient;
        this.configuration = configuration;
    }

    public Maybe<List<NinetyNineCompany>> fetchCompanies() {
        String path = "/{api}/{apiversion}/companies/";
        String uri = UriTemplate.of(path).expand(configuration.toMap());

        System.out.print("URI: " + uri);

        HttpRequest<?> req = HttpRequest.GET(uri);  
        Flowable flowable = httpClient.retrieve(req, Argument.of(List.class, NinetyNineCompany.class)); 
        return (Maybe<List<NinetyNineCompany>>) flowable.firstElement(); 
    }

    public Maybe<NinetyNineCompanyInfo> fetchCompanyDetails(Long id) {
        String path = "/{api}/{apiversion}/companies/" + id.toString();
        String uri = UriTemplate.of(path).expand(configuration.toMap());

        HttpRequest<?> req = HttpRequest.GET(uri);  
        Flowable flowable = httpClient.retrieve(req, Argument.of(NinetyNineCompanyInfo.class)); 
        return (Maybe<NinetyNineCompanyInfo>) flowable.firstElement(); 
    }
}