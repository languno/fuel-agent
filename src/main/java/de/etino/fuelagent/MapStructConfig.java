package de.etino.fuelagent;

import de.etino.fuelagent.gasstation.GasStation;
import de.etino.fuelagent.gasstation.diariosur.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructConfig {
    GasStation map(Marker marker);
    Marker map(GasStation gasStation);
}
