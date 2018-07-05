package de.etino.fuelagent.gasstation.diariosur;

import de.etino.fuelagent.MapStructConfig;
import de.etino.fuelagent.gasstation.GasStation;
import de.etino.fuelagent.gasstation.GasStationPrice;
import de.etino.fuelagent.gasstation.GasStationPriceRepository;
import de.etino.fuelagent.gasstation.GasStationRepository;
import feign.Feign;
import feign.Logger;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GasStationsDiariosurService {

    private static final String GASOLINERAS_DIARIOSUR_URL = "http://gasolineras.diariosur.es";
    private static final String PROVINCE_MALAGA_CODE = "5653";
    private static final String PROVINCE_MALAGA = "Malaga";
    private static final String SPAIN = "ES";

    @Autowired
    private MapStructConfig mapper;

    @Autowired
    GasStationRepository gasStationRepository;

    @Autowired
    GasStationPriceRepository gasStationPriceRepository;

    public void collectAndPersistData() {

        List<GasStationDiarioSur> priceList = getGasStationsPriceList();
        log.info("Collected list from diario sur with {} entires", priceList.size());

        updateGasStationsAndPriceList(priceList);
    }

    private List<GasStationDiarioSur> getGasStationsPriceList() {

        GasStationsDiariosurClient client = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JAXBDecoder(new JAXBContextFactory.Builder().build()))
                .logger(new Slf4jLogger(GasStationsDiariosurClient.class))
                .logLevel(Logger.Level.FULL)
                .target(GasStationsDiariosurClient.class, GASOLINERAS_DIARIOSUR_URL);

        try {
            Markers diarioMarkers = client.getGasStationsPriceList(PROVINCE_MALAGA_CODE);

            List<GasStationDiarioSur> gasStationList = diarioMarkers.marker.stream()
                    .map(mapper::map)
                    .collect(Collectors.toList());

            LocalDateTime now = LocalDateTime.now();

            gasStationList.forEach(station -> {
                station.setDateTime(now);
                station.setCountryCode(SPAIN);
                station.setProvince("Malaga");
            });

            return gasStationList;
        } catch (RuntimeException re) {
            log.error("Error getting price list from diariosur", re);
            return new ArrayList<>();
        }
    }

    private void updateGasStationsAndPriceList(List<GasStationDiarioSur> priceList) {

        List<GasStation> gasStations = priceList.stream()
                .map(priceEntry -> GasStation.builder()
                            .address(priceEntry.getAddress())
                            .brand(priceEntry.getBrand())
                            .countryCode(priceEntry.getCountryCode())
                            .province(priceEntry.getProvince())
                            .latitude(priceEntry.getLatitude())
                            .longitude(priceEntry.getLongitude())
                            .build())
                .distinct()
                .collect(Collectors.toList());

        log.info("gas station prices collected: " + gasStations.size());

        updateGasStations(gasStations);

        saveGasStationPriceList(gasStations, priceList);
    }

    void updateGasStations(List<GasStation> gasStations) {

        List<GasStation> savedGasStations = gasStationRepository
                .findByCountryCodeAndProvince(SPAIN, PROVINCE_MALAGA)
                .orElse(new ArrayList<>());

        List<GasStation> newStations = gasStations.stream()
                .filter(station -> !savedGasStations.contains(station))
                .collect(Collectors.toList());

        if (newStations.size() > 0) {
            log.info("Save {} new gas stations", newStations.size());
            gasStationRepository.saveAll(newStations);
            gasStationRepository.flush();
        }

    }

    void saveGasStationPriceList(List<GasStation> gasStations, List<GasStationDiarioSur> priceList) {

        List<GasStationPrice> prices = new ArrayList<>();
        for (GasStationDiarioSur priceEntry: priceList) {
            Optional<GasStation> gasStation = gasStations.stream()
                    .filter(station -> station.getLatitude().equals(priceEntry.getLatitude()))
                    .filter(station -> station.getLongitude().equals(priceEntry.getLongitude()))
                    .findAny();

            if (!gasStation.isPresent()) {
                log.error("Could not find gas station for price entry: {}", priceEntry);
            } else {
                prices.add(GasStationPrice.builder()
                        .gasStation(gasStation.get())
                        .dateTime(priceEntry.getDateTime())
                        .dieselPrice(priceEntry.getDieselPrice())
                        .gasoline95Price(priceEntry.getGasoline95Price())
                        .gasoline98Price(priceEntry.getGasoline98Price())
                        .build());
            }
        }

        gasStationPriceRepository.saveAll(prices);
        gasStationPriceRepository.flush();
    }
}
