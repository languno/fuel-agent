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
    GasStationRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<GasStation> getPriceListForGasStation(@RequestParam String countryCode, @RequestParam String province) {
        log.info("Get gas stations for country {}, province {}", countryCode, province);
        return repo.findByCountryCodeAndProvince(countryCode, province)
                .orElseThrow(() -> new IllegalArgumentException("Country or province not found"));
    }
}
