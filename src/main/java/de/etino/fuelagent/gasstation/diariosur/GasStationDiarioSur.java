package de.etino.fuelagent.gasstation.diariosur;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GasStationDiarioSur {

    private String countryCode;
    private String province;
    private String brand;
    private String address;
    private String latitude;
    private String longitude;

    private LocalDateTime dateTime;
    private String dieselPrice;
    private String gasoline95Price;
    private String gasoline98Price;
}
