package de.etino.fuelagent.gasstation.diariosur;

import de.etino.fuelagent.gasstation.GasStation;
import de.etino.fuelagent.gasstation.GasStationPrice;
import de.etino.fuelagent.gasstation.GasStationPriceRepository;
import de.etino.fuelagent.gasstation.GasStationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GasStationsDiariosurServiceTest {

    @InjectMocks
    GasStationsDiariosurService service;

    @Mock
    GasStationRepository gasStationRepository;

    @Mock
    GasStationPriceRepository gasStationPriceRepository;

    private static final String LAT1 = "1.123";
    private static final String LON1 = "1.321";
    private static final String LAT2 = "2.123";
    private static final String LON2 = "2.321";
    private static final String DIESEL1 = "1.15";
    private static final String DIESEL2 = "1.18";
    private static final LocalDateTime DATE1 = LocalDateTime.now().minusHours(1);
    private static final LocalDateTime DATE2 = LocalDateTime.now().minusHours(2);

    GasStation gasStation1 = GasStation.builder()
            .address("Adr1")
            .brand("Shell")
            .countryCode("ES")
            .longitude(LON1)
            .latitude(LAT1)
            .build();
    GasStation gasStation2 = GasStation.builder()
            .address("Adr2")
            .brand("Shell")
            .countryCode("ES")
            .longitude(LON2)
            .latitude(LAT2)
            .build();

    GasStationDiarioSur fetchedStation1 = GasStationDiarioSur.builder()
            .address("Adr1")
            .brand("Shell")
            .longitude(LON1)
            .latitude(LAT1)
            .dieselPrice(DIESEL1)
            .dateTime(DATE1)
            .build();

    GasStationDiarioSur fetchedStation2 = GasStationDiarioSur.builder()
            .address("Adr2")
            .brand("Shell")
            .longitude(LON2)
            .latitude(LAT2)
            .dieselPrice(DIESEL2)
            .dateTime(DATE2)
            .build();

    GasStationPrice expectedPrice1 = GasStationPrice.builder()
            .gasStation(gasStation1)
            .dieselPrice(DIESEL1)
            .dateTime(DATE1)
            .build();

    GasStationPrice expectedPrice2 = GasStationPrice.builder()
            .gasStation(gasStation2)
            .dieselPrice(DIESEL2)
            .dateTime(DATE2)
            .build();

    @Test
    public void testUpdateGasStations() {
        List<GasStation> persistedGasStations = Arrays.asList(gasStation2);
        List<GasStation> fetchedGasStations = Arrays.asList(gasStation1, gasStation2);
        List<GasStation> newGasStations = Arrays.asList(gasStation1);

        when(gasStationRepository.findByCountryCodeAndProvince(any(), any()))
                .thenReturn(Optional.of(persistedGasStations));

        service.updateGasStations(fetchedGasStations);

        verify(gasStationRepository).saveAll(newGasStations);
    }

    @Test
    public void testSaveGasStationPriceList() {
        List<GasStation> givenGasStations = Arrays.asList(gasStation1, gasStation2);
        List<GasStationDiarioSur> fetchedPriceList = Arrays.asList(fetchedStation1, fetchedStation2);
        List<GasStationPrice> expectedPriceList = Arrays.asList(expectedPrice1, expectedPrice2);

        when(gasStationRepository.findByCountryCodeAndProvince(any(), any()))
                .thenReturn(Optional.of(givenGasStations));

        service.saveGasStationPriceList(fetchedPriceList);

        verify(gasStationPriceRepository).saveAll(expectedPriceList);
    }
}
