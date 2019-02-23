package example.micronaut.repositories;

import example.micronaut.ApplicationConfiguration;
import example.micronaut.domain.Company;
import example.micronaut.domain.Price;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Singleton 
public class PriceRepositoryImpl implements PriceRepository {

    @PersistenceContext
    private EntityManager entityManager; 
    private final ApplicationConfiguration applicationConfiguration;

    public PriceRepositoryImpl(@CurrentSession EntityManager entityManager,
                               ApplicationConfiguration applicationConfiguration) { 
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true) 
    public Optional<Price> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Price.class, id));
    }

    @Override
    @Transactional 
    public Price save(@NotNull Float shareprice, @NotNull Date datetime, @NotNull Company company) {
        Price price = new Price(shareprice, datetime, company);
        entityManager.persist(price);
        return price;
    }

    @Transactional(readOnly = true)
    public List<Price> findAll(@NotNull Long id, Date startDate, Date endDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String qlString = "SELECT p FROM Price as p WHERE p.datetime BETWEEN '" +  dateFormat.format(startDate) + "' AND '" + dateFormat.format(endDate) + "' AND company_id = " + id.toString() + " ORDER BY p.datetime ASC";
        TypedQuery<Price> query = entityManager.createQuery(qlString, Price.class);
        return query.getResultList();
    }
}
