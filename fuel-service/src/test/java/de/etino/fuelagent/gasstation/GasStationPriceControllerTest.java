package de.etino.fuelagent.gasstation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import util.TimeUtils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GasStationPriceControllerTest {

    private final static Long GASSTATION_ID = 1234L;

    @Mock
    private GasStationPriceRepository repo;


    private Clock fixedClock = TimeUtils.getFixedClock();

    private GasStationPriceController controller;

    @Before
    public void init() {
        controller = new GasStationPriceController(repo, fixedClock);
    }

    @Test
    public void testGetPriceListForGasStationForDays() {

        Long days = 5L;
        ArgumentCaptor<Long> gasStationIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<LocalDateTime> dateTimeCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        controller.getPriceListForGasStationForDays(GASSTATION_ID, days);

        verify(repo).findByGasStationIdAndDateTimeAfter(gasStationIdCaptor.capture(), dateTimeCaptor.capture());

        LocalDateTime expectedTime = TimeUtils.REFERENCE_DATE_TIME
                .minus(days - 1, ChronoUnit.DAYS).withHour(0).withMinute(0);

        assertThat(gasStationIdCaptor.getValue(), is(GASSTATION_ID));
        assertThat(dateTimeCaptor.getValue(), is(expectedTime));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPriceListForGasStationForDaysWithIlleagalArgument() {

        Long days = 0L;

        controller.getPriceListForGasStationForDays(GASSTATION_ID, days);
    }

    @Test
    public void testGetPriceListForGasStation() {
        controller.getPriceListForGasStation(GASSTATION_ID);

        verify(repo).findByGasStationId(GASSTATION_ID);
    }
}
