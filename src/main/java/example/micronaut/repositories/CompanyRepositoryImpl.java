package example.micronaut.repositories;

import example.micronaut.ApplicationConfiguration;
import example.micronaut.SortingAndOrderArguments;
import example.micronaut.domain.Company;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton 
public class CompanyRepositoryImpl implements CompanyRepository {

    @PersistenceContext
    private EntityManager entityManager; 
    private final ApplicationConfiguration applicationConfiguration;

    public CompanyRepositoryImpl(@CurrentSession EntityManager entityManager,
                               ApplicationConfiguration applicationConfiguration) { 
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true) 
    public Optional<Company> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Company.class, id));
    }

    @Override
    @Transactional 
    public Company save(@NotBlank Long id, @NotBlank String name, @NotBlank String ric, Float shareprice, String description, String country) {
        Company company = new Company(id, name, ric, shareprice, description, country);
        entityManager.persist(company);
        return company;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(company -> entityManager.remove(company));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name", "ric", "description", "shareprice", "sharePrice", "country");

    @Transactional(readOnly = true)
    public List<Company> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT c FROM Company as c";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
                qlString += " ORDER BY c." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Company> query = entityManager.createQuery(qlString, Company.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        return query.getResultList();
    }

    @Override
    @Transactional
    public int update(@NotNull Long id, @NotBlank String name, @NotBlank String ric, Float shareprice,  String description, String country) {
        return entityManager.createQuery("UPDATE Company c SET name = :name, ric = :ric, sharePrice = :shareprice, description = :description, country = :country where id = :id")
                .setParameter("name", name)
                .setParameter("ric", ric)
                .setParameter("shareprice", shareprice)
                .setParameter("description", description)
                .setParameter("country", country)
                .setParameter("id", id)
                .executeUpdate();
    }
}
