package example.micronaut.repositories;

import example.micronaut.SortingAndOrderArguments;
import example.micronaut.domain.Company;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Optional<Company> findById(@NotNull Long id);

    Company save(@NotBlank Long id, @NotBlank String name, @NotBlank String ric, Float shareprice, String description, String country);

    void deleteById(@NotNull Long id);

    List<Company> findAll(@NotNull SortingAndOrderArguments args);

    int update(@NotNull Long id, @NotBlank String name, @NotBlank String ric, Float shareprice, String description, String country);
}
