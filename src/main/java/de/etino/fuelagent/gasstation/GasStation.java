package de.etino.fuelagent.gasstation;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "gas_station")
public class GasStation {

    @Id
    @GeneratedValue
    Long id;

    private LocalDateTime dateTime;
    private String countryCode;
    private String province;

    private String brand;
    private String address;
    private String dieselPrice;
    private String gasoline95Price;
    private String gasoline98Price;
    private String latitude;
    private String longitude;
}
