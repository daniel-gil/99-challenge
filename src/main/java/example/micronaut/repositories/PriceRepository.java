package example.micronaut.repositories;

import example.micronaut.domain.Company;
import example.micronaut.domain.Price;

import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findById(@NotNull Long id);

    Price save(@NotNull Float shareprice, @NotNull Date datetime, @NotNull Company company);

    List<Price> findAll(@NotNull Long id, Date startDate, Date endDate);
}
