package de.etino.fuelagent.gasstation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    Optional<List<GasStation>> findByCountryCodeAndProvince(String countryCode, String province);
}
