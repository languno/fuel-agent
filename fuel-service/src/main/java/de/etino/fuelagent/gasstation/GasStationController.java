package de.etino.fuelagent.gasstation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/gasStation")
public class GasStationController {

    @Autowired
    GasStationService gasStationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<GasStationWithPrice> getPriceListForGasStation(@RequestParam String countryCode, @RequestParam String province) {

        log.info("Get gas stations for country {}, province {}", countryCode, province);
        return gasStationService.getPriceListForGasStation(countryCode, province);
    }
}
