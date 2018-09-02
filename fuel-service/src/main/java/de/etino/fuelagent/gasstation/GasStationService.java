package de.etino.fuelagent.gasstation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GasStationService {

    @Autowired
    GasStationRepository repo;

    @Autowired
    private GasStationPriceRepository gasStationPriceRepository;

    public List<GasStationWithPrice> getPriceListForGasStation(String countryCode, String province) {

        List<GasStation> gasStations = repo.findByCountryCodeAndProvince(countryCode, province)
                .orElseThrow(() -> new IllegalArgumentException("Country or province not found"));

        return gasStations.stream()
                .map(gasStation -> {
                    GasStationPrice price = gasStationPriceRepository
                            .findFirstByGasStationIdOrderByDateTimeDesc(gasStation.getId());
                    return GasStationWithPrice.builder()
                            .gasStation(gasStation)
                            .currentPrice(price)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
