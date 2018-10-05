package de.etino.fuelagent.gasstation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/gasStationPrice")
public class GasStationPriceController {

    @Autowired
    private GasStationPriceRepository repo;

    @Autowired
    Clock clock;

    @RequestMapping(value = "/{gasStationId}", method = RequestMethod.GET)
    public List<GasStationPrice> getPriceListForGasStation(@PathVariable Long gasStationId) {
        log.info("Get price list for gasStationId = {}", gasStationId);
        return repo.findByGasStationId(gasStationId);
    }

    @RequestMapping(value = "/{gasStationId}/forDays/{days}", method = RequestMethod.GET)
    public List<GasStationPrice> getPriceListForGasStationForDays(@PathVariable Long gasStationId, @PathVariable Long days) {

        if (days < 1) {
            throw new IllegalArgumentException("Interval must be at least 1 day");
        }

        log.info("Get price list for gasStationId = {} for {} days", gasStationId, days);
        LocalDate date = LocalDate.now(clock).minusDays(days - 1);
        return repo.findByGasStationIdAndDateTimeAfter(gasStationId, LocalDateTime.of(date, LocalTime.MIDNIGHT));
    }
}
