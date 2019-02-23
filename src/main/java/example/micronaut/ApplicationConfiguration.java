package example.micronaut;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(ApplicationConfiguration.PREFIX)
@Requires(property = ApplicationConfiguration.PREFIX)
public class ApplicationConfiguration {

    public static final String PREFIX = "ninetynine";
    public static final String NINETYNINE_API_URL = "https://dev.ninetynine.com/";
    
    protected final Integer DEFAULT_MAX = 10;
    private Integer max = DEFAULT_MAX;

    private String apiversion;
    private String api;
    private String endpoint;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        if(max != null) {
            this.max = max;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApiversion() {
        return apiversion;
    }

    public void setApiversion(String apiversion) {
        this.apiversion = apiversion;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("endpoint", getEndpoint());
        m.put("apiversion", getApiversion());
        m.put("api", getApi());
        return m;
    }
}
