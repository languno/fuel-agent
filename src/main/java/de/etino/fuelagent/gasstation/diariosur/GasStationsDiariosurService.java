package de.etino.fuelagent.gasstation.diariosur;

import feign.Feign;
import feign.Logger;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.stereotype.Service;

@Service
public class GasStationsDiariosurService {

    private static final String GASOLINERAS_DIARIOSUR_URL = "http://gasolineras.diariosur.es";
    private static final String PROVINCE_MALAGA = "5653";

    public GasStations getGasStationsPriceList() {

        GasStationsDiariosurClient client = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new JAXBDecoder(new JAXBContextFactory.Builder().build()))
                .logger(new Slf4jLogger(GasStationsDiariosurClient.class))
                .logLevel(Logger.Level.FULL)
                .target(GasStationsDiariosurClient.class, GASOLINERAS_DIARIOSUR_URL);

        return client.getGasStationsPriceList(PROVINCE_MALAGA);
    }
}
