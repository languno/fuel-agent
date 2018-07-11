package de.etino.fuelagent;

import de.etino.fuelagent.gasstation.diariosur.GasStationDiarioSur;
import de.etino.fuelagent.gasstation.diariosur.Marker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructConfig {
    GasStationDiarioSur map(Marker marker);
}
