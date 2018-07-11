package de.etino.fuelagent.gasstation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/gasStationPrice")
public class GasStationPriceController {

    @Autowired
    private GasStationPriceRepository repo;

    @RequestMapping(value = "/{gasStationId}", method = RequestMethod.GET)
    public List<GasStationPrice> getPriceListForGasStation(@PathVariable Long gasStationId) {
        log.info("Get price list for gasStationId = {}", gasStationId);
        return repo.findByGasStationId(gasStationId);
    }
}
