package de.etino.fuelagent.gasstation.diariosur;

import de.etino.fuelagent.MapStructConfig;
import de.etino.fuelagent.gasstation.GasStation;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class GasStationsDiariosurService {

    private static final String GASOLINERAS_DIARIOSUR_URL = "http://gasolineras.diariosur.es";
    private static final String PROVINCE_MALAGA = "5653";
    private static final String SPAIN = "ES";

    @Autowired
    private MapStructConfig mapper;

    public List<GasStation> getGasStationsPriceList() {

        GasStationsDiariosurClient client = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JAXBDecoder(new JAXBContextFactory.Builder().build()))
                .logger(new Slf4jLogger(GasStationsDiariosurClient.class))
                .logLevel(Logger.Level.FULL)
                .target(GasStationsDiariosurClient.class, GASOLINERAS_DIARIOSUR_URL);

        try {
            Markers diarioMarkers = client.getGasStationsPriceList(PROVINCE_MALAGA);

            List<GasStation> gasStationList = diarioMarkers.marker.stream()
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
}
