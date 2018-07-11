package de.etino.fuelagent.gasstation;

import de.etino.fuelagent.gasstation.diariosur.GasStationsDiariosurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GasStationService {

    @Autowired
    GasStationsDiariosurService gasStationsDiariosurService;

    public void collectAndPersistData() {
        gasStationsDiariosurService.collectAndPersistData();
    }
}
