package de.etino.fuelagent.gasstation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GasStationPriceRepository extends JpaRepository<GasStationPrice, Long> {
    List<GasStationPrice> findByGasStationId(long gasStationId);
    List<GasStationPrice> findByGasStationIdAndDateTimeAfter(long gasStationId, LocalDateTime dateTime);
}
