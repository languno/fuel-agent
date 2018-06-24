package de.etino.fuelagent.gasstation.diariosur;

import feign.Param;
import feign.RequestLine;

public interface GasStationsDiariosurClient {

    @RequestLine("GET /mapa_xml.php?provincia={province}&municipio=&combustible=")
    GasStations getGasStationsPriceList(@Param("province") String province);
}
