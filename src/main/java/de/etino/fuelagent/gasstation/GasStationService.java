package de.etino.fuelagent.gasstation;

import de.etino.fuelagent.gasstation.diariosur.GasStationsDiariosurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GasStationService {

    @Autowired
    GasStationRepository repo;

    @Autowired
    GasStationsDiariosurService gasStationsDiariosurService;

    public void collectAndPersistData() {

        List<GasStation> priceList = gasStationsDiariosurService.getGasStationsPriceList();
        log.info("Collected list from diario sur with {} entires", priceList != null ? priceList.size() : 0);

        saveGasStationPriceList(priceList);
    }

    private void saveGasStationPriceList(List<GasStation> priceList) {
        repo.saveAll(priceList);
    }
}
